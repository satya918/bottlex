import {
  useEffect,
  useMemo,
  useState,
} from "react";

import {
  Plus,
  Search,
  Package2,
  AlertTriangle,
  CalendarClock,
  Pencil,
  Trash2,
  Power,
  X,
  ArrowLeft,

} from "lucide-react";
import { useNavigate } from "react-router-dom";


import toast from "react-hot-toast";

import apiClient from "../api/apiClient";

interface Product {

  id: string;

  productName: string;
}

interface Batch {

  id: string;

  batchNumber: string;

  quantity: number;

  remainingQuantity: number;

  manufacturingDate: string;

  expiryDate: string;

  active: boolean;

  productId: string;

  productName: string;
}

export default function Batches() {

  const navigate = useNavigate();


  // =========================================
  // STATE
  // =========================================

  const [loading, setLoading] =
    useState(true);

  const [page, setPage] =
    useState(0);

  const [pageSize] =
    useState(10);

  const [search, setSearch] =
    useState("");

  const [products, setProducts] =
    useState<Product[]>([]);

  const [batches, setBatches] =
    useState<Batch[]>([]);

  const [showModal, setShowModal] =
    useState(false);

  const [showEditModal, setShowEditModal] =
    useState(false);

  const [selectedBatch, setSelectedBatch] =
    useState<Batch | null>(null);

  const [formData, setFormData] =
    useState({
      batchNumber: "",
      quantity: 0,
      manufacturingDate: "",
      expiryDate: "",
      productId: "",
    });

  // =========================================
  // FETCH BATCHES
  // =========================================

  const fetchBatches = async () => {

    try {

      setLoading(true);

      const response =
        await apiClient.get(
          "/api/admin/batches"
        );

      setBatches(response.data);

    } catch (error) {

      console.log(error);

      toast.error(
        "Failed to fetch batches"
      );

    } finally {

      setLoading(false);
    }
  };

  // =========================================
  // FETCH PRODUCTS
  // =========================================

  const fetchProducts = async () => {

    try {

      const response =
        await apiClient.get(
          "/api/products"
        );

      setProducts(response.data.content);
    } catch (error) {

      console.log(error);
    }
  };

  useEffect(() => {

    fetchBatches();

    fetchProducts();

  }, []);

  // =========================================
  // CREATE BATCH
  // =========================================

  const createBatch = async () => {

    try {

      if (
        !formData.batchNumber ||
        !formData.productId ||
        !formData.manufacturingDate ||
        !formData.expiryDate
      ) {

        toast.error(
          "Please fill all fields"
        );

        return;
      }

      if (
        formData.quantity <= 0
      ) {

        toast.error(
          "Quantity must be greater than 0"
        );

        return;
      }

      await apiClient.post(
        "/api/admin/batches",
        formData
      );

      toast.success(
        "Batch created successfully"
      );

      setShowModal(false);

      resetForm();

      fetchBatches();

    } catch (error) {

      console.log(error);

      toast.error(
        "Failed to create batch"
      );
    }
  };

  // =========================================
  // UPDATE BATCH
  // =========================================

  const updateBatch = async () => {

    try {

      if (!selectedBatch) return;

      await apiClient.put(
        `/api/admin/batches/${selectedBatch.id}`,
        {
          batchNumber:
            selectedBatch.batchNumber,

          quantity:
            selectedBatch.quantity,

          manufacturingDate:
            selectedBatch.manufacturingDate,

          expiryDate:
            selectedBatch.expiryDate,

          productId:
            selectedBatch.productId,
        }
      );

      toast.success(
        "Batch updated successfully"
      );

      setShowEditModal(false);

      setSelectedBatch(null);

      fetchBatches();

    } catch (error) {

      console.log(error);

      toast.error(
        "Failed to update batch"
      );
    }
  };

  // =========================================
  // DELETE
  // =========================================

  const deleteBatch = async (
    id: string
  ) => {

    const confirmed =
      window.confirm(
        "Delete this batch?"
      );

    if (!confirmed) return;

    try {

      await apiClient.delete(
        `/api/admin/batches/${id}`
      );

      toast.success(
        "Batch deleted"
      );

      fetchBatches();

    } catch (error) {

      console.log(error);

      toast.error(
        "Failed to delete batch"
      );
    }
  };

  // =========================================
  // TOGGLE STATUS
  // =========================================

  const toggleStatus = async (
    batch: Batch
  ) => {

    try {

      await apiClient.patch(
        `/api/admin/batches/${batch.id}/status?active=${!batch.active}`
      );

      toast.success(
        "Status updated"
      );

      fetchBatches();

    } catch (error) {

      console.log(error);

      toast.error(
        "Failed to update status"
      );
    }
  };

  // =========================================
  // RESET FORM
  // =========================================

  const resetForm = () => {

    setFormData({
      batchNumber: "",
      quantity: 0,
      manufacturingDate: "",
      expiryDate: "",
      productId: "",
    });
  };

  // =========================================
  // FILTER
  // =========================================

  const filteredBatches =
    useMemo(() => {

      return batches.filter(
        (batch) =>
          batch.batchNumber
            .toLowerCase()
            .includes(
              search.toLowerCase()
            ) ||
          batch.productName
            .toLowerCase()
            .includes(
              search.toLowerCase()
            )
      );

    }, [batches, search]);

  // =========================================
  // PAGINATION
  // =========================================

  const totalPages =
    Math.ceil(
      filteredBatches.length / pageSize
    );

  const paginatedBatches =
    filteredBatches.slice(
      page * pageSize,
      page * pageSize + pageSize
    );

  // =========================================
  // STATS
  // =========================================

  const totalBatches =
    batches.length;

  const activeBatches =
    batches.filter(
      b => b.active
    ).length;

  const lowStockBatches =
    batches.filter(
      b => b.remainingQuantity < 10
    ).length;

  const expiringSoon =
    batches.filter((batch) => {

      const today =
        new Date();

      const expiry =
        new Date(
          batch.expiryDate
        );

      const diff =
        expiry.getTime() -
        today.getTime();

      const days =
        diff /
        (
          1000 *
          60 *
          60 *
          24
        );

      return days <= 30;

    }).length;

  return (

    <div className="
            min-h-screen
            bg-black
            text-white
            p-8
        ">

      {/* ========================================= */}
      {/* HEADER */}
      {/* ========================================= */}

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
            Batch Management
          </h1>

          <p className="
                        text-gray-400
                        mt-2
                    ">
            Manage inventory batches
          </p>

        </div>

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

          Create Batch

        </button>

      </div>

      {/* ========================================= */}
      {/* STATS */}
      {/* ========================================= */}

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
                        items-center
                    ">

            <div>

              <p className="
                                text-gray-400
                            ">
                Total Batches
              </p>

              <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                {totalBatches}
              </h2>

            </div>

            <Package2
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
                        items-center
                    ">

            <div>

              <p className="
                                text-gray-400
                            ">
                Active
              </p>

              <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                {activeBatches}
              </h2>

            </div>

            <Power
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
                        items-center
                    ">

            <div>

              <p className="
                                text-gray-400
                            ">
                Low Stock
              </p>

              <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                {lowStockBatches}
              </h2>

            </div>

            <AlertTriangle
              size={40}
              className="
                                text-red-500
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
                        items-center
                    ">

            <div>

              <p className="
                                text-gray-400
                            ">
                Expiring Soon
              </p>

              <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                {expiringSoon}
              </h2>

            </div>

            <CalendarClock
              size={40}
              className="
                                text-orange-500
                            "
            />

          </div>

        </div>

      </div>

      {/* ========================================= */}
      {/* SEARCH */}
      {/* ========================================= */}

      <div className="
                bg-[#111]
                border border-[#222]
                rounded-2xl
                p-4
                flex items-center gap-3
                mb-6
            ">

        <Search
          size={20}
          className="
                        text-gray-500
                    "
        />

        <input
          type="text"
          placeholder="Search batches..."
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
                        placeholder:text-gray-500
                    "
        />

      </div>

      {/* ========================================= */}
      {/* TABLE */}
      {/* ========================================= */}

      <div className="
                bg-[#111]
                border border-[#222]
                rounded-3xl
                overflow-hidden
            ">

        <table className="w-full">

          <thead className="
                        bg-[#181818]
                    ">

            <tr className="
                            text-gray-400
                        ">

              <th className="
                                text-left p-5
                            ">
                Batch
              </th>

              <th className="
                                text-left p-5
                            ">
                Product
              </th>

              <th className="
                                text-left p-5
                            ">
                Quantity
              </th>

              <th className="
                                text-left p-5
                            ">
                Remaining
              </th>

              <th className="
                                text-left p-5
                            ">
                MFG
              </th>

              <th className="
                                text-left p-5
                            ">
                EXP
              </th>

              <th className="
                                text-left p-5
                            ">
                Status
              </th>

              <th className="
                                text-left p-5
                            ">
                Actions
              </th>

            </tr>

          </thead>

          <tbody>

            {loading ? (

              <tr>

                <td
                  colSpan={8}
                  className="
                                        text-center
                                        p-10
                                    "
                >
                  Loading batches...
                </td>

              </tr>

            ) : paginatedBatches.length === 0 ? (

              <tr>

                <td
                  colSpan={8}
                  className="
                                        text-center
                                        p-10
                                        text-gray-500
                                    "
                >
                  No batches found
                </td>

              </tr>

            ) : (

              paginatedBatches.map(
                (batch) => {

                  const expiry =
                    new Date(
                      batch.expiryDate
                    );

                  const today =
                    new Date();

                  const diff =
                    expiry.getTime() -
                    today.getTime();

                  const days =
                    diff /
                    (
                      1000 *
                      60 *
                      60 *
                      24
                    );

                  const expiring =
                    days <= 30;

                  return (

                    <tr
                      key={batch.id}
                      className="
                                                border-t
                                                border-[#222]
                                                hover:bg-[#181818]
                                            "
                    >

                      <td className="p-5">
                        {
                          batch.batchNumber
                        }
                      </td>

                      <td className="p-5">
                        {
                          batch.productName
                        }
                      </td>

                      <td className="p-5">
                        {
                          batch.quantity
                        }
                      </td>

                      <td className="p-5">

                        <span className={
                          batch.remainingQuantity < 10
                            ? "text-red-400"
                            : "text-green-400"
                        }>
                          {
                            batch.remainingQuantity
                          }
                        </span>

                      </td>

                      <td className="p-5">
                        {
                          batch.manufacturingDate
                        }
                      </td>

                      <td className="p-5">

                        <span className={
                          expiring
                            ? "text-red-400"
                            : "text-green-400"
                        }>
                          {
                            batch.expiryDate
                          }
                        </span>

                      </td>

                      <td className="p-5">

                        {batch.active ? (

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

                        <div className="
                                                    flex gap-3
                                                ">

                          <button
                            onClick={() => {

                              setSelectedBatch(
                                batch
                              );

                              setShowEditModal(
                                true
                              );

                            }}
                            className="
                                                            bg-blue-500/10
                                                            hover:bg-blue-500/20
                                                            text-blue-400
                                                            p-2
                                                            rounded-xl
                                                        "
                          >

                            <Pencil size={16} />

                          </button>

                          <button
                            onClick={() =>
                              deleteBatch(
                                batch.id
                              )
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

                          <button
                            onClick={() =>
                              toggleStatus(
                                batch
                              )
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

                        </div>

                      </td>

                    </tr>
                  );
                }
              )
            )}

          </tbody>

        </table>

      </div>

      {/* ========================================= */}
      {/* PAGINATION */}
      {/* ========================================= */}

      <div className="
                flex justify-center
                gap-4
                mt-6
            ">

        <button
          disabled={page === 0}
          onClick={() =>
            setPage(
              prev => prev - 1
            )
          }
          className="
                        px-4 py-2
                        rounded-xl
                        bg-white/10
                        disabled:opacity-30
                    "
        >
          Previous
        </button>

        <span className="
                    flex items-center
                ">
          Page {page + 1} of {totalPages}
        </span>

        <button
          disabled={
            page + 1 >= totalPages
          }
          onClick={() =>
            setPage(
              prev => prev + 1
            )
          }
          className="
                        px-4 py-2
                        rounded-xl
                        bg-white/10
                        disabled:opacity-30
                    "
        >
          Next
        </button>

      </div>

      {/* ========================================= */}
      {/* CREATE MODAL */}
      {/* ========================================= */}

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
                        max-w-2xl
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
                Create Batch
              </h2>

              <button
                onClick={() =>
                  setShowModal(false)
                }
                className="
                                    text-gray-400
                                "
              >
                <X />
              </button>

            </div>

            <div className="
                            grid grid-cols-2
                            gap-4
                        ">

              <input
                placeholder="Batch Number"
                value={
                  formData.batchNumber
                }
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    batchNumber:
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
              />

              <input
                type="number"
                placeholder="Quantity"
                value={
                  formData.quantity
                }
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    quantity:
                      Number(
                        e.target.value
                      ),
                  })
                }
                className="
                                    bg-[#1a1a1a]
                                    border border-[#333]
                                    rounded-xl
                                    p-4
                                    outline-none
                                "
              />

              <input
                type="date"
                value={
                  formData.manufacturingDate
                }
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    manufacturingDate:
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
              />

              <input
                type="date"
                value={
                  formData.expiryDate
                }
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    expiryDate:
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
              />

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
                                    col-span-2
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
                onClick={createBatch}
                className="
                                    px-5 py-3
                                    rounded-xl
                                    bg-yellow-500
                                    hover:bg-yellow-400
                                    text-black
                                    font-semibold
                                "
              >
                Create Batch
              </button>

            </div>

          </div>

        </div>
      )}

    </div>
  );
}