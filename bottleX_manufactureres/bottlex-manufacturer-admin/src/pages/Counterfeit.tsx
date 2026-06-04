import {
    useState,
} from "react";

import {
    ShieldCheck,
    ShieldAlert,
    Search,
    ScanLine,
} from "lucide-react";

import toast from "react-hot-toast";

import { VerificationAPI }
    from "../api/verifiction.api";

export default function VerifyProductPage() {

    const [qrCode, setQrCode] =
        useState("");

    const [loading, setLoading] =
        useState(false);

    const [result, setResult] =
        useState<any>(null);

    const verifyProduct = async () => {

        try {

            if (!qrCode) {

                toast.error(
                    "Enter QR code"
                );

                return;
            }

            setLoading(true);

            const res =
                await VerificationAPI
                    .verify(qrCode);

            setResult(res.data);

        } catch (error) {

            console.log(error);

            toast.error(
                "Verification failed"
            );

        } finally {

            setLoading(false);
        }
    };

    return (

        <div className="
            min-h-screen
            bg-black
            text-white
            flex items-center
            justify-center
            p-8
        ">

            <div className="
                w-full
                max-w-2xl
                bg-[#111]
                border border-[#222]
                rounded-3xl
                p-10
            ">

                <div className="
                    flex items-center gap-3
                    mb-8
                ">

                    <ScanLine
                        size={40}
                        className="
                            text-yellow-500
                        "
                    />

                    <div>

                        <h1 className="
                            text-4xl
                            font-bold
                        ">
                            Verify Product
                        </h1>

                        <p className="
                            text-gray-400
                            mt-1
                        ">
                            Enterprise anti-counterfeit verification
                        </p>

                    </div>

                </div>

                {/* INPUT */}

                <div className="
                    flex gap-3
                    mb-6
                ">

                    <div className="
                        flex-1
                        bg-[#1a1a1a]
                        border border-[#333]
                        rounded-2xl
                        px-4
                        flex items-center
                    ">

                        <Search
                            size={18}
                            className="
                                text-gray-500
                            "
                        />

                        <input
                            type="text"
                            placeholder="Enter QR Code"
                            value={qrCode}
                            onChange={(e) =>
                                setQrCode(
                                    e.target.value
                                )
                            }
                            className="
                                bg-transparent
                                outline-none
                                w-full
                                p-4
                            "
                        />

                    </div>

                    <button
                        onClick={verifyProduct}
                        disabled={loading}
                        className="
                            bg-yellow-500
                            hover:bg-yellow-400
                            text-black
                            px-6
                            rounded-2xl
                            font-semibold
                        "
                    >

                        {loading
                            ? "Verifying..."
                            : "Verify"}

                    </button>

                </div>

                {/* RESULT */}

                {result && (

                    <div className={`
                        rounded-3xl
                        p-8
                        border

                        ${result.genuine
                            ? `
                                bg-green-500/10
                                border-green-500/20
                            `
                            : `
                                bg-red-500/10
                                border-red-500/20
                            `
                        }
                    `}>

                        <div className="
                            flex items-center gap-4
                            mb-6
                        ">

                            {result.genuine ? (

                                <ShieldCheck
                                    size={60}
                                    className="
                                        text-green-400
                                    "
                                />

                            ) : (

                                <ShieldAlert
                                    size={60}
                                    className="
                                        text-red-400
                                    "
                                />

                            )}

                            <div>

                                <h2 className="
                                    text-3xl
                                    font-bold
                                ">

                                    {result.genuine
                                        ? "GENUINE PRODUCT"
                                        : "FAKE PRODUCT"}

                                </h2>

                                <p className="
                                    mt-2
                                    text-lg
                                ">
                                    {result.message}
                                </p>

                            </div>

                        </div>

                        {result.genuine && (

                            <div className="
                                grid
                                grid-cols-2
                                gap-6
                            ">

                                <div>

                                    <p className="
                                        text-gray-400
                                    ">
                                        Product
                                    </p>

                                    <h3 className="
                                        text-xl
                                        font-semibold
                                        mt-1
                                    ">
                                        {
                                            result.productName
                                        }
                                    </h3>

                                </div>

                                <div>

                                    <p className="
                                        text-gray-400
                                    ">
                                        Batch
                                    </p>

                                    <h3 className="
                                        text-xl
                                        font-semibold
                                        mt-1
                                    ">
                                        {
                                            result.batchNumber
                                        }
                                    </h3>

                                </div>

                            </div>
                        )}

                        {result.suspicious && (

                            <div className="
                                mt-6
                                bg-yellow-500/10
                                border border-yellow-500/20
                                text-yellow-400
                                rounded-2xl
                                p-4
                            ">

                                Warning:
                                Multiple scans detected.
                                Product may be suspicious.

                            </div>
                        )}

                    </div>
                )}

            </div>

        </div>
    );
}