import {
    useEffect,
    useState,
} from "react";

import {
    ShieldAlert,
    ShieldCheck,
    AlertTriangle,
    QrCode,
    Package,
    Factory,
    Plus,
} from "lucide-react";

import AdminLayout from "../layouts/AdminLayout";

import apiClient from "../api/apiClient";

interface DashboardStats {

    totalScans: number;

    authenticBottles: number;

    counterfeitAlerts: number;

    duplicateQr: number;
}

interface Alert {

    id: string;

    product: string;

    location: string;

    scans: number;

    risk: string;
}

interface ProductFraud {

    name: string;

    fakePercent: string;
}

interface Batch {

    id: string;

    batchNumber: string;

    productName: string;

    quantity: number;

    status: string;
}

interface DistributorRisk {

    id: string;

    distributorName: string;

    fakeAlerts: number;

    risk: string;
}

export default function Dashboard() {

    const [loading, setLoading] =
        useState(true);

    const [stats, setStats] =
        useState<DashboardStats>({
            totalScans: 0,
            authenticBottles: 0,
            counterfeitAlerts: 0,
            duplicateQr: 0,
        });

    const [alerts, setAlerts] =
        useState<Alert[]>([]);

    const [products, setProducts] =
        useState<ProductFraud[]>([]);

    const [batches, setBatches] =
        useState<Batch[]>([]);

    const [distributors, setDistributors] =
        useState<DistributorRisk[]>([]);

    useEffect(() => {

        fetchDashboard();

    }, []);

    const fetchDashboard = async () => {

        try {

            setLoading(true);

            const [
                statsRes,
                alertsRes,
                productsRes,
                batchesRes,
                distributorsRes,
            ] = await Promise.all([

                apiClient.get(
                    "/api/admin/dashboard/stats"
                ),

                apiClient.get(
                    "/api/admin/dashboard/counterfeit-alerts"
                ),

                apiClient.get(
                    "/api/admin/dashboard/product-fraud"
                ),

                apiClient.get(
                    "/api/admin/dashboard/recent-batches"
                ),

                apiClient.get(
                    "/api/admin/dashboard/distributor-risk"
                ),
            ]);

            setStats(statsRes.data);

            setAlerts(alertsRes.data);

            setProducts(productsRes.data);

            setBatches(batchesRes.data);

            setDistributors(
                distributorsRes.data
            );

        } catch (error) {

            console.log(error);

        } finally {

            setLoading(false);
        }
    };

    if (loading) {

        return (

            <AdminLayout>

                <div className="
                    min-h-screen
                    bg-black
                    text-white
                    flex items-center
                    justify-center
                ">

                    Loading dashboard...

                </div>

            </AdminLayout>
        );
    }

    return (

        <AdminLayout>

            <div className="
                p-8
                min-h-screen
                bg-black
                text-white
            ">

                {/* HEADER */}

                <div className="
                    flex items-center
                    justify-between
                    mb-10
                ">

                    <div>

                        <h1 className="
                            text-4xl
                            font-bold
                        ">
                            Manufacturer Dashboard
                        </h1>

                        <p className="
                            text-gray-400
                            mt-2
                        ">
                            Monitor counterfeit bottles & QR activity
                        </p>

                    </div>

                    <button className="
                        bg-yellow-500
                        hover:bg-yellow-400
                        text-black
                        px-5
                        py-3
                        rounded-2xl
                        font-semibold
                        transition
                    ">
                        Company Admin
                    </button>

                </div>

                {/* KPI CARDS */}

                <div className="
                    grid
                    grid-cols-1
                    md:grid-cols-2
                    xl:grid-cols-4
                    gap-6
                    mb-10
                ">

                    {/* TOTAL */}

                    <div className="
                        bg-zinc-900
                        border border-zinc-800
                        rounded-3xl
                        p-6
                    ">

                        <div className="
                            flex items-center
                            justify-between
                        ">

                            <div>

                                <p className="
                                    text-gray-400
                                ">
                                    Total Scans
                                </p>

                                <h2 className="
                                    text-4xl
                                    font-bold
                                    text-yellow-400
                                    mt-4
                                ">
                                    {stats.totalScans}
                                </h2>

                            </div>

                            <QrCode
                                size={45}
                                className="
                                    text-yellow-400
                                "
                            />

                        </div>

                    </div>

                    {/* AUTHENTIC */}

                    <div className="
                        bg-zinc-900
                        border border-zinc-800
                        rounded-3xl
                        p-6
                    ">

                        <div className="
                            flex items-center
                            justify-between
                        ">

                            <div>

                                <p className="
                                    text-gray-400
                                ">
                                    Authentic Bottles
                                </p>

                                <h2 className="
                                    text-4xl
                                    font-bold
                                    text-green-400
                                    mt-4
                                ">
                                    {
                                        stats.authenticBottles
                                    }
                                </h2>

                            </div>

                            <ShieldCheck
                                size={45}
                                className="
                                    text-green-400
                                "
                            />

                        </div>

                    </div>

                    {/* COUNTERFEIT */}

                    <div className="
                        bg-zinc-900
                        border border-zinc-800
                        rounded-3xl
                        p-6
                    ">

                        <div className="
                            flex items-center
                            justify-between
                        ">

                            <div>

                                <p className="
                                    text-gray-400
                                ">
                                    Counterfeit Alerts
                                </p>

                                <h2 className="
                                    text-4xl
                                    font-bold
                                    text-red-400
                                    mt-4
                                ">
                                    {
                                        stats.counterfeitAlerts
                                    }
                                </h2>

                            </div>

                            <ShieldAlert
                                size={45}
                                className="
                                    text-red-400
                                "
                            />

                        </div>

                    </div>

                    {/* DUPLICATE */}

                    <div className="
                        bg-zinc-900
                        border border-zinc-800
                        rounded-3xl
                        p-6
                    ">

                        <div className="
                            flex items-center
                            justify-between
                        ">

                            <div>

                                <p className="
                                    text-gray-400
                                ">
                                    Duplicate QR
                                </p>

                                <h2 className="
                                    text-4xl
                                    font-bold
                                    text-orange-400
                                    mt-4
                                ">
                                    {stats.duplicateQr}
                                </h2>

                            </div>

                            <AlertTriangle
                                size={45}
                                className="
                                    text-orange-400
                                "
                            />

                        </div>

                    </div>

                </div>

                {/* MAIN GRID */}

                <div className="
                    grid
                    grid-cols-1
                    xl:grid-cols-3
                    gap-6
                ">

                    {/* ALERTS */}

                    <div className="
                        xl:col-span-2
                        bg-zinc-900
                        border border-zinc-800
                        rounded-3xl
                        p-6
                    ">

                        <div className="
                            flex items-center
                            justify-between
                            mb-6
                        ">

                            <h2 className="
                                text-2xl
                                font-bold
                            ">
                                Live Counterfeit Alerts
                            </h2>

                            <button className="
                                text-yellow-400
                            ">
                                View All
                            </button>

                        </div>

                        <div className="
                            space-y-4
                        ">

                            {alerts.map((alert) => (

                                <div
                                    key={alert.id}
                                    className="
                                        bg-zinc-800
                                        rounded-2xl
                                        p-5
                                        flex
                                        items-center
                                        justify-between
                                    "
                                >

                                    <div>

                                        <h3 className="
                                            text-lg
                                            font-semibold
                                        ">
                                            {alert.product}
                                        </h3>

                                        <p className="
                                            text-gray-400
                                            mt-1
                                        ">
                                            {alert.location}
                                        </p>

                                    </div>

                                    <div className="
                                        flex items-center
                                        gap-8
                                    ">

                                        <div>

                                            <p className="
                                                text-sm
                                                text-gray-400
                                            ">
                                                Duplicate Scans
                                            </p>

                                            <p className="
                                                text-xl
                                                font-bold
                                                text-yellow-400
                                            ">
                                                {alert.scans}
                                            </p>

                                        </div>

                                        <div className={`
                                            px-4
                                            py-2
                                            rounded-xl
                                            font-semibold

                                            ${alert.risk === "HIGH"
                                                ? `
                                                    bg-red-500/20
                                                    text-red-400
                                                `
                                                : `
                                                    bg-orange-500/20
                                                    text-orange-400
                                                `
                                            }
                                        `}>

                                            {alert.risk}

                                        </div>

                                    </div>

                                </div>
                            ))}

                        </div>

                    </div>

                    {/* PRODUCT FRAUD */}

                    <div className="
                        bg-zinc-900
                        border border-zinc-800
                        rounded-3xl
                        p-6
                    ">

                        <h2 className="
                            text-2xl
                            font-bold
                            mb-6
                        ">
                            Product Fraud Analytics
                        </h2>

                        <div className="
                            space-y-5
                        ">

                            {products.map(
                                (item, index) => (

                                <div
                                    key={index}
                                    className="
                                        bg-zinc-800
                                        rounded-2xl
                                        p-5
                                    "
                                >

                                    <div className="
                                        flex items-center
                                        justify-between
                                    ">

                                        <div>

                                            <p className="
                                                text-lg
                                                font-semibold
                                            ">
                                                {item.name}
                                            </p>

                                            <p className="
                                                text-sm
                                                text-gray-400
                                                mt-1
                                            ">
                                                Counterfeit Detection Rate
                                            </p>

                                        </div>

                                        <div className="
                                            text-2xl
                                            font-bold
                                            text-red-400
                                        ">
                                            {item.fakePercent}
                                        </div>

                                    </div>

                                </div>
                            ))}

                        </div>

                    </div>

                </div>

                {/* BOTTOM GRID */}

                <div className="
                    grid
                    grid-cols-1
                    xl:grid-cols-2
                    gap-6
                    mt-6
                ">

                    {/* BATCHES */}

                    <div className="
                        bg-zinc-900
                        border border-zinc-800
                        rounded-3xl
                        p-6
                    ">

                        <div className="
                            flex items-center
                            justify-between
                            mb-6
                        ">

                            <h2 className="
                                text-2xl
                                font-bold
                            ">
                                Recent Production Batches
                            </h2>

                            <button className="
                                bg-yellow-500
                                hover:bg-yellow-400
                                text-black
                                px-4
                                py-2
                                rounded-xl
                                font-semibold
                                flex items-center
                                gap-2
                            ">

                                <Plus size={16} />

                                Create Batch

                            </button>

                        </div>

                        <div className="
                            space-y-4
                        ">

                            {batches.map((batch) => (

                                <div
                                    key={batch.id}
                                    className="
                                        bg-zinc-800
                                        rounded-2xl
                                        p-5
                                        flex
                                        items-center
                                        justify-between
                                    "
                                >

                                    <div>

                                        <p className="
                                            font-semibold
                                        ">
                                            {
                                                batch.batchNumber
                                            }
                                        </p>

                                        <p className="
                                            text-gray-400
                                            text-sm
                                            mt-1
                                        ">
                                            {
                                                batch.productName
                                            } · {
                                                batch.quantity
                                            } Bottles
                                        </p>

                                    </div>

                                    <div className={`
                                        font-semibold

                                        ${batch.status === "ACTIVE"
                                            ? `
                                                text-green-400
                                            `
                                            : `
                                                text-yellow-400
                                            `
                                        }
                                    `}>

                                        {batch.status}

                                    </div>

                                </div>
                            ))}

                        </div>

                    </div>

                    {/* DISTRIBUTOR */}

                    <div className="
                        bg-zinc-900
                        border border-zinc-800
                        rounded-3xl
                        p-6
                    ">

                        <h2 className="
                            text-2xl
                            font-bold
                            mb-6
                        ">
                            Distributor Risk Monitoring
                        </h2>

                        <div className="
                            space-y-4
                        ">

                            {distributors.map(
                                (dist) => (

                                <div
                                    key={dist.id}
                                    className="
                                        bg-zinc-800
                                        rounded-2xl
                                        p-5
                                        flex
                                        items-center
                                        justify-between
                                    "
                                >

                                    <div>

                                        <p className="
                                            text-lg
                                            font-semibold
                                        ">
                                            {
                                                dist.distributorName
                                            }
                                        </p>

                                        <p className="
                                            text-gray-400
                                            text-sm
                                            mt-1
                                        ">
                                            Fake Alerts:
                                            {" "}
                                            {
                                                dist.fakeAlerts
                                            }
                                        </p>

                                    </div>

                                    <div className={`
                                        px-4
                                        py-2
                                        rounded-xl
                                        font-semibold

                                        ${dist.risk === "HIGH"
                                            ? `
                                                bg-red-500/20
                                                text-red-400
                                            `
                                            : `
                                                bg-orange-500/20
                                                text-orange-400
                                            `
                                        }
                                    `}>

                                        {dist.risk} RISK

                                    </div>

                                </div>
                            ))}

                        </div>

                    </div>

                </div>

            </div>

        </AdminLayout>
    );
}