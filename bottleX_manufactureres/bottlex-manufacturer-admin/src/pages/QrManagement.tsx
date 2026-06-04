import {
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";

import {
  Plus,
  Search,
  QrCode,
  ScanLine,
  Download,
  Printer,
  Trash2,
  Power,
  Package,
  Boxes,
  ArrowLeft,
} from "lucide-react";

import { useNavigate } from "react-router-dom";

import * as QRCode from "qrcode";
import toast from "react-hot-toast";

import html2canvas from "html2canvas";

import jsPDF from "jspdf";

import { saveAs } from "file-saver";

import { QRAPI } from "../api/qr.api";

import apiClient from "../api/apiClient";

interface Product {

  id: string;

  productName: string;
}

interface Batch {

  id: string;

  batchNumber: string;
}

interface QRData {

  id: string;

  qrCode: string;

  qrType: string;

  active: boolean;

  productName: string;

  batchNumber: string;

  createdAt: string;

  lastScannedAt: string;
}

export default function QRManagementPage() {

  const navigate = useNavigate();

  // ====================================================
  // STATE
  // ====================================================

  const [loading, setLoading] =
    useState(true);

  const [search, setSearch] =
    useState("");

  const [products, setProducts] =
    useState<Product[]>([]);

  const [batches, setBatches] =
    useState<Batch[]>([]);

  const [qrCodes, setQrCodes] =
    useState<QRData[]>([]);

  const [showModal, setShowModal] =
    useState(false);

  const [selectedQR, setSelectedQR] =
    useState<QRData | null>(null);

  const qrRef =
    useRef<HTMLDivElement>(null);

  const [formData, setFormData] =
    useState({
      productId: "",
      batchId: "",
      qrType: "BATCH",
    });

  // ====================================================
  // FETCH
  // ====================================================

  const fetchData = async () => {

    try {

      setLoading(true);

      const [
        qrRes,
        productRes,
        batchRes,
      ] = await Promise.all([

        QRAPI.getAll(),

        apiClient.get(
          "/api/products"
        ),

        apiClient.get(
          "/api/admin/batches"
        ),
      ]);

      setQrCodes(
        Array.isArray(qrRes.data)
          ? qrRes.data
          : []
      );

      setProducts(
        Array.isArray(productRes.data)
          ? productRes.data
          : productRes.data.content || []
      );

      setBatches(
        Array.isArray(batchRes.data)
          ? batchRes.data
          : batchRes.data.content || []
      );

    } catch (error) {

      console.log(error);

      toast.error(
        "Failed to load QR data"
      );

    } finally {

      setLoading(false);
    }
  };

  useEffect(() => {

    fetchData();

  }, []);

  // ====================================================
  // GENERATE QR
  // ====================================================

  const generateQR = async () => {

    try {

      if (
        !formData.productId ||
        !formData.batchId
      ) {

        toast.error(
          "Please select all fields"
        );

        return;
      }

      await QRAPI.generate(
        formData
      );

      toast.success(
        "QR generated successfully"
      );

      setShowModal(false);

      fetchData();

    } catch (error) {

      console.log(error);

      toast.error(
        "Failed to generate QR"
      );
    }
  };

  // ====================================================
  // DELETE QR
  // ====================================================

  const deleteQR = async (
    id: string
  ) => {

    const confirmed =
      window.confirm(
        "Delete this QR Code?"
      );

    if (!confirmed) return;

    try {

      await QRAPI.delete(id);

      toast.success(
        "QR deleted"
      );

      fetchData();

    } catch (error) {

      console.log(error);

      toast.error(
        "Delete failed"
      );
    }
  };

  // ====================================================
  // TOGGLE STATUS
  // ====================================================

  const toggleStatus = async (
    qr: QRData
  ) => {

    try {

      await QRAPI.toggleStatus(
        qr.id,
        !qr.active
      );

      toast.success(
        "Status updated"
      );

      fetchData();

    } catch (error) {

      console.log(error);

      toast.error(
        "Failed"
      );
    }
  };

  // ====================================================
  // DOWNLOAD QR
  // ====================================================

  const downloadQR = async (
    qrCode: string
  ) => {

    try {

      const canvas =
        document.createElement(
          "canvas"
        );

      await QRCode.toCanvas(
        canvas,
        qrCode
      );

      canvas.toBlob((blob) => {

        if (!blob) return;

        saveAs(
          blob,
          `${qrCode}.png`
        );

      });

    } catch (error) {

      console.log(error);
    }
  };

  // ====================================================
  // PRINT PDF
  // ====================================================

  const printPDF = async () => {

    try {

      const printElement =
        document.createElement("div");

      printElement.style.padding = "20px";

      printElement.style.background =
        "#ffffff";

      printElement.style.color =
        "#000000";

      qrCodes.forEach((qr) => {

        const item =
          document.createElement("div");

        item.style.marginBottom =
          "20px";

        item.innerHTML = `
                <div>
                    <h3>${qr.productName}</h3>
                    <p>${qr.batchNumber}</p>
                    <p>${qr.qrCode}</p>
                </div>
            `;

        printElement.appendChild(item);
      });

      document.body.appendChild(
        printElement
      );

      const canvas =
        await html2canvas(
          printElement,
          {
            backgroundColor:
              "#ffffff",
          }
        );

      const imgData =
        canvas.toDataURL(
          "image/png"
        );

      const pdf =
        new jsPDF();

      pdf.addImage(
        imgData,
        "PNG",
        10,
        10,
        180,
        0
      );

      pdf.save(
        "qr-report.pdf"
      );

      document.body.removeChild(
        printElement
      );

    } catch (error) {

      console.log(error);

      toast.error(
        "PDF generation failed"
      );
    }
  };

  // ====================================================
  // FILTER
  // ====================================================

  const filteredQRs =
    useMemo(() => {

      return qrCodes.filter(
        (qr) =>
          qr.qrCode
            .toLowerCase()
            .includes(
              search.toLowerCase()
            ) ||
          qr.productName
            .toLowerCase()
            .includes(
              search.toLowerCase()
            )
      );

    }, [qrCodes, search]);

  // ====================================================
  // STATS
  // ====================================================

  const activeQRs =
    qrCodes.filter(
      q => q.active
    ).length;

  const scannedQRs =
    qrCodes.filter(
      q => q.lastScannedAt
    ).length;

  // ====================================================
  // UI
  // ====================================================

  return (

    <div className="
            min-h-screen
            bg-black
            text-white
            p-8
        ">

      {/* ================================================= */}
      {/* HEADER */}
      {/* ================================================= */}

      <div className="
                flex justify-between
                items-center
                mb-8
            ">

        <div>

          <button
            onClick={() => navigate(-1)}
            className="
            flex items-center gap-2
            mb-4
            text-gray-400
            hover:text-white
            transition
        "
          >

            <ArrowLeft size={18} />

            Back

          </button>

          <h1 className="
        text-4xl
        font-bold
    ">
            QR Management
          </h1>

          <p className="
        text-gray-400
        mt-2
    ">
            Enterprise QR inventory tracking
          </p>

        </div>

        <div className="
                    flex gap-3
                ">

          <button
            onClick={printPDF}
            className="
                            flex items-center gap-2
                            bg-white/10
                            hover:bg-white/20
                            px-5 py-3
                            rounded-2xl
                        "
          >

            <Printer size={18} />

            Print PDF

          </button>

          <button
            onClick={() =>
              setShowModal(true)
            }
            className="
                            flex items-center gap-2
                            bg-yellow-500
                            hover:bg-yellow-400
                            text-black
                            px-5 py-3
                            rounded-2xl
                            font-semibold
                        "
          >

            <Plus size={18} />

            Generate QR

          </button>

        </div>

      </div>

      {/* ================================================= */}
      {/* STATS */}
      {/* ================================================= */}

      <div className="
                grid
                grid-cols-1 md:grid-cols-4
                gap-6
                mb-8
            ">

        <div className="
                    bg-[#111]
                    border border-[#222]
                    rounded-3xl
                    p-6
                ">

          <div className="
                        flex justify-between
                    ">

            <div>

              <p className="
                                text-gray-400
                            ">
                Total QR
              </p>

              <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                {qrCodes.length}
              </h2>

            </div>

            <QrCode
              size={40}
              className="
                                text-yellow-500
                            "
            />

          </div>

        </div>

        <div className="
                    bg-[#111]
                    border border-[#222]
                    rounded-3xl
                    p-6
                ">

          <div className="
                        flex justify-between
                    ">

            <div>

              <p className="
                                text-gray-400
                            ">
                Active QR
              </p>

              <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                {activeQRs}
              </h2>

            </div>

            <Package
              size={40}
              className="
                                text-green-500
                            "
            />

          </div>

        </div>

        <div className="
                    bg-[#111]
                    border border-[#222]
                    rounded-3xl
                    p-6
                ">

          <div className="
                        flex justify-between
                    ">

            <div>

              <p className="
                                text-gray-400
                            ">
                Scanned
              </p>

              <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                {scannedQRs}
              </h2>

            </div>

            <ScanLine
              size={40}
              className="
                                text-blue-500
                            "
            />

          </div>

        </div>

        <div className="
                    bg-[#111]
                    border border-[#222]
                    rounded-3xl
                    p-6
                ">

          <div className="
                        flex justify-between
                    ">

            <div>

              <p className="
                                text-gray-400
                            ">
                Batches
              </p>

              <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                {batches.length}
              </h2>

            </div>

            <Boxes
              size={40}
              className="
                                text-orange-500
                            "
            />

          </div>

        </div>

      </div>

      {/* ================================================= */}
      {/* SEARCH */}
      {/* ================================================= */}

      <div className="
                bg-[#111]
                border border-[#222]
                rounded-2xl
                p-4
                mb-6
                flex items-center gap-3
            ">

        <Search
          size={20}
          className="
                        text-gray-500
                    "
        />

        <input
          type="text"
          placeholder="Search QR..."
          value={search}
          onChange={(e) =>
            setSearch(
              e.target.value
            )
          }
          className="
                        bg-transparent
                        outline-none
                        flex-1
                    "
        />

      </div>

      {/* ================================================= */}
      {/* TABLE */}
      {/* ================================================= */}

      <div
        ref={qrRef}
        className="
                    bg-[#111]
                    border border-[#222]
                    rounded-3xl
                    overflow-hidden
                "
      >

        <table className="w-full">

          <thead className="
                        bg-[#181818]
                        text-gray-400
                    ">

            <tr>

              <th className="p-5 text-left">
                QR Code
              </th>

              <th className="p-5 text-left">
                Product
              </th>

              <th className="p-5 text-left">
                Batch
              </th>

              <th className="p-5 text-left">
                Type
              </th>

              <th className="p-5 text-left">
                Status
              </th>

              <th className="p-5 text-left">
                Last Scan
              </th>

              <th className="p-5 text-left">
                Actions
              </th>

            </tr>

          </thead>

          <tbody>

            {loading ? (

              <tr>

                <td
                  colSpan={7}
                  className="
                                        text-center
                                        p-10
                                    "
                >
                  Loading...
                </td>

              </tr>

            ) : filteredQRs.length === 0 ? (

              <tr>

                <td
                  colSpan={7}
                  className="
                                        text-center
                                        p-10
                                    "
                >
                  No QR codes found
                </td>

              </tr>

            ) : (

              filteredQRs.map(
                (qr) => (

                  <tr
                    key={qr.id}
                    className="
                                            border-t
                                            border-[#222]
                                            hover:bg-[#181818]
                                        "
                  >

                    <td className="p-5">

                      <div className="
                                                flex items-center gap-3
                                            ">

                        <div
                          id={qr.qrCode}
                          className="
                                                        bg-white
                                                        p-2
                                                        rounded-lg
                                                    "
                        />

                        <div>

                          <p className="
                                                        text-sm
                                                    ">
                            {
                              qr.qrCode.substring(
                                0,
                                12
                              )
                            }...
                          </p>

                        </div>

                      </div>

                    </td>

                    <td className="p-5">
                      {
                        qr.productName
                      }
                    </td>

                    <td className="p-5">
                      {
                        qr.batchNumber
                      }
                    </td>

                    <td className="p-5">
                      {
                        qr.qrType
                      }
                    </td>

                    <td className="p-5">

                      {qr.active ? (

                        <span className="
                                                    bg-green-500/10
                                                    text-green-400
                                                    border border-green-500/20
                                                    px-3 py-1
                                                    rounded-full
                                                    text-sm
                                                ">
                          ACTIVE
                        </span>

                      ) : (

                        <span className="
                                                    bg-red-500/10
                                                    text-red-400
                                                    border border-red-500/20
                                                    px-3 py-1
                                                    rounded-full
                                                    text-sm
                                                ">
                          INACTIVE
                        </span>

                      )}

                    </td>

                    <td className="p-5">

                      {
                        qr.lastScannedAt
                          ? new Date(
                            qr.lastScannedAt
                          ).toLocaleString()
                          : "-"
                      }

                    </td>

                    <td className="p-5">

                      <div className="
                                                flex gap-3
                                            ">

                        <button
                          onClick={() =>
                            downloadQR(
                              qr.qrCode
                            )
                          }
                          className="
                                                        bg-blue-500/10
                                                        hover:bg-blue-500/20
                                                        text-blue-400
                                                        p-2
                                                        rounded-xl
                                                    "
                        >

                          <Download size={16} />

                        </button>

                        <button
                          onClick={() =>
                            toggleStatus(qr)
                          }
                          className="
                                                        bg-yellow-500/10
                                                        hover:bg-yellow-500/20
                                                        text-yellow-400
                                                        p-2
                                                        rounded-xl
                                                    "
                        >

                          <Power size={16} />

                        </button>

                        <button
                          onClick={() =>
                            deleteQR(qr.id)
                          }
                          className="
                                                        bg-red-500/10
                                                        hover:bg-red-500/20
                                                        text-red-400
                                                        p-2
                                                        rounded-xl
                                                    "
                        >

                          <Trash2 size={16} />

                        </button>

                      </div>

                    </td>

                  </tr>
                )
              )
            )}

          </tbody>

        </table>

      </div>

      {/* ================================================= */}
      {/* MODAL */}
      {/* ================================================= */}

      {showModal && (

        <div className="
                    fixed inset-0
                    bg-black/70
                    flex items-center justify-center
                    z-50
                ">

          <div className="
                        bg-[#111]
                        border border-[#222]
                        rounded-3xl
                        p-8
                        w-full
                        max-w-xl
                    ">

            <div className="
                            flex justify-between
                            items-center
                            mb-6
                        ">

              <h2 className="
                                text-2xl
                                font-bold
                            ">
                Generate QR
              </h2>

              <button
                onClick={() =>
                  setShowModal(false)
                }
              >
                ✕
              </button>

            </div>

            <div className="
                            flex flex-col gap-4
                        ">

              <select
                value={
                  formData.productId
                }
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    productId:
                      e.target.value,
                  })
                }
                className="
                                    bg-[#1a1a1a]
                                    border border-[#333]
                                    rounded-xl
                                    p-4
                                    outline-none
                                "
              >

                <option value="">
                  Select Product
                </option>

                {products.map(
                  (product) => (

                    <option
                      key={product.id}
                      value={product.id}
                    >
                      {
                        product.productName
                      }
                    </option>
                  )
                )}

              </select>

              <select
                value={
                  formData.batchId
                }
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    batchId:
                      e.target.value,
                  })
                }
                className="
                                    bg-[#1a1a1a]
                                    border border-[#333]
                                    rounded-xl
                                    p-4
                                    outline-none
                                "
              >

                <option value="">
                  Select Batch
                </option>

                {batches.map(
                  (batch) => (

                    <option
                      key={batch.id}
                      value={batch.id}
                    >
                      {
                        batch.batchNumber
                      }
                    </option>
                  )
                )}

              </select>

              <select
                value={
                  formData.qrType
                }
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    qrType:
                      e.target.value,
                  })
                }
                className="
                                    bg-[#1a1a1a]
                                    border border-[#333]
                                    rounded-xl
                                    p-4
                                    outline-none
                                "
              >

                <option value="PRODUCT">
                  PRODUCT
                </option>

                <option value="BATCH">
                  BATCH
                </option>

                <option value="SERIAL">
                  SERIAL
                </option>

                <option value="SHIPMENT">
                  SHIPMENT
                </option>

              </select>

            </div>

            <div className="
                            flex justify-end
                            gap-4
                            mt-8
                        ">

              <button
                onClick={() =>
                  setShowModal(false)
                }
                className="
                                    px-5 py-3
                                    rounded-xl
                                    bg-[#222]
                                "
              >
                Cancel
              </button>

              <button
                onClick={generateQR}
                className="
                                    px-5 py-3
                                    rounded-xl
                                    bg-yellow-500
                                    hover:bg-yellow-400
                                    text-black
                                    font-semibold
                                "
              >
                Generate QR
              </button>

            </div>

          </div>

        </div>
      )}

    </div>
  );
}