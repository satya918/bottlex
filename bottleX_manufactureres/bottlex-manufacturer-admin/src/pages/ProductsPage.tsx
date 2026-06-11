import {
    useEffect,
    useState,
} from "react";
import { useNavigate } from "react-router-dom";
import {
    Plus,
    Search,
    Package,
    Boxes,
    AlertTriangle,
    Pencil,
    Trash2,
    Power,
    X,
    ArrowLeft,

} from "lucide-react";

import toast from "react-hot-toast";

import { useProducts } from "../hooks/useProducts";

import { ProductsAPI } from "../api/products.api";

export default function ProductsPage() {

    // =========================================
    // STATE
    // =========================================
    const navigate = useNavigate();
    const [page, setPage] = useState(0);

    const [search, setSearch] =
        useState("");

    const [categories, setCategories] =
        useState<any[]>([]);

    const [showModal, setShowModal] =
        useState(false);

    const [showEditModal, setShowEditModal] =
        useState(false);

    const [selectedProduct, setSelectedProduct] =
        useState<any>(null);

    const [formData, setFormData] =
        useState({
            productName: "",
            productCode: "",
            sku: "",
            description: "",
            categoryId: "",
            price: "",
            stockQuantity: "",
        });

    const {
        products,
        totalPages,
        loading,
        refetch,
    } = useProducts(page, search);

    // =========================================
    // EFFECTS
    // =========================================

    useEffect(() => {

        fetchCategories();

    }, []);

    // =========================================
    // API CALLS
    // =========================================

    const fetchCategories = async () => {

        try {

            const res =
                await ProductsAPI.getCategories();

            setCategories(res.data);

        } catch (err) {

            console.log(err);

            toast.error(
                "Failed to fetch categories"
            );
        }
    };

    const resetForm = () => {

        setFormData({
            productName: "",
            productCode: "",
            sku: "",
            description: "",
            categoryId: "",
            price: "",
            stockQuantity: "",
           
        });
    };

    const createProduct = async () => {

        try {

            if (
                !formData.productName ||
                !formData.productCode ||
                !formData.sku ||
                !formData.categoryId
            ) {

                toast.error(
                    "Please fill all required fields"
                );

                return;
            }

            await ProductsAPI.createProduct({
                ...formData,
                price: Number(formData.price),
                stockQuantity:
                    Number(formData.stockQuantity),
            });

            toast.success(
                "Product created successfully"
            );

            setShowModal(false);

            resetForm();

            refetch();

        } catch (err) {

            console.log(err);

            toast.error(
                "Failed to create product"
            );
        }
    };

    const updateProduct = async () => {

        try {

            await ProductsAPI.updateProduct(
                selectedProduct.id,
                selectedProduct
            );

            toast.success(
                "Product updated successfully"
            );

            setShowEditModal(false);

            setSelectedProduct(null);

            refetch();

        } catch (err) {

            console.log(err);

            toast.error(
                "Failed to update product"
            );
        }
    };

    const deleteProduct = async (
        id: string
    ) => {

        const confirmDelete =
            window.confirm(
                "Are you sure you want to delete this product?"
            );

        if (!confirmDelete) return;

        try {

            await ProductsAPI.deleteProduct(id);

            toast.success(
                "Product deleted successfully"
            );

            refetch();

        } catch (err) {

            console.log(err);

            toast.error(
                "Failed to delete product"
            );
        }
    };

    const toggleStatus = async (
        product: any
    ) => {

        try {

            await ProductsAPI.toggleStatus(
                product.id,
                !product.active
            );

            toast.success(
                "Status updated"
            );

            refetch();

        } catch (err) {

            console.log(err);

            toast.error(
                "Failed to update status"
            );
        }
    };

    // =========================================
    // UI
    // =========================================

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
                flex
                justify-between
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
                        Products
                    </h1>

                    <p className="
                        text-gray-400
                        mt-2
                    ">
                        Manage inventory and products
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
                        transition
                    "
                >

                    <Plus size={18} />

                    Add Product

                </button>

                <button
                    onClick={() =>
                        navigate("/categories")
                    }
                    className="
                        flex items-center gap-2
                        bg-yellow-500
                        hover:bg-yellow-400
                        text-black
                        px-5 py-3
                        rounded-2xl
                        font-semibold
                        transition
                    "
                >

                    <Plus size={18} />

                    Maintain Categories

                </button>

            </div>

            {/* ========================================= */}
            {/* STATS */}
            {/* ========================================= */}

            <div className="
                grid
                grid-cols-1 md:grid-cols-3
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
                        flex
                        justify-between
                        items-center
                    ">

                        <div>

                            <p className="text-gray-400">
                                Total Products
                            </p>

                            <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                                {products.length}
                            </h2>

                        </div>

                        <Package
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
                        flex
                        justify-between
                        items-center
                    ">

                        <div>

                            <p className="text-gray-400">
                                Active Products
                            </p>

                            <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                                {
                                    products.filter(
                                        p => p.active
                                    ).length
                                }
                            </h2>

                        </div>

                        <Boxes
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
                        flex
                        justify-between
                        items-center
                    ">

                        <div>

                            <p className="text-gray-400">
                                Low Stock
                            </p>

                            <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                                {
                                    products.filter(
                                        p =>
                                            p.stockQuantity < 10
                                    ).length
                                }
                            </h2>

                        </div>

                        <AlertTriangle
                            className="
                                text-red-500
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

                <Search className="text-gray-500" />

                <input
                    placeholder="Search products..."
                    value={search}
                    onChange={(e) =>
                        setSearch(e.target.value)
                    }
                    className="
                        bg-transparent
                        outline-none
                        w-full
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

                    <thead className="bg-[#1a1a1a]">

                        <tr className="text-gray-400">

                            <th className="p-5 text-left">
                                Product
                            </th>

                            <th className="p-5 text-left">
                                SKU
                            </th>

                            <th className="p-5 text-left">
                                Category
                            </th>

                            <th className="p-5 text-left">
                                Price
                            </th>

                            <th className="p-5 text-left">
                                Stock
                            </th>

                            <th className="p-5 text-left">
                                Status
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

                        ) : products.length === 0 ? (

                            <tr>

                                <td
                                    colSpan={7}
                                    className="
                                        text-center
                                        p-10
                                        text-gray-500
                                    "
                                >
                                    No Active products found
                                </td>

                            </tr>

                        ) : (

                            products.map((product) => (

                                <tr
                                    key={product.id}
                                    className="
                                        border-t
                                        border-[#222]
                                        hover:bg-[#181818]
                                    "
                                >

                                    <td className="p-5">

                                        <div>

                                            <p className="font-semibold">
                                                {
                                                    product.productName
                                                }
                                            </p>

                                            <p className="
                                                text-sm
                                                text-gray-500
                                            ">
                                                {
                                                    product.productCode
                                                }
                                            </p>

                                        </div>

                                    </td>

                                    <td className="p-5">
                                        {product.sku}
                                    </td>

                                    <td className="p-5">
                                        {
                                            product.categoryName

                                        }
                                    </td>

                                    <td className="p-5">
                                        ₹ {product.price}
                                    </td>

                                    <td className="p-5">

                                        <span className={`
                                            px-3 py-1
                                            rounded-full
                                            text-sm

                                            ${product.stockQuantity < 10
                                                ? `
                                                    bg-red-500/10
                                                    text-red-400
                                                `
                                                : `
                                                    bg-green-500/10
                                                    text-green-400
                                                `
                                            }
                                        `}>
                                            {
                                                product.stockQuantity
                                            }
                                        </span>

                                    </td>

                                    <td className="p-5">

                                        {product.active ? (

                                            <span className="
                                                bg-green-500/10
                                                text-green-400
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
                                            flex gap-2
                                        ">

                                            <button
                                                onClick={() => {

                                                    setSelectedProduct(product);

                                                    setShowEditModal(true);

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
                                                    deleteProduct(
                                                        product.id
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
                                                    toggleStatus(product)
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
                            ))
                        )}

                    </tbody>

                </table>

            </div>

            {/* ========================================= */}
            {/* PAGINATION */}
            {/* ========================================= */}

            <div className="
                flex
                justify-center
                items-center
                gap-4
                mt-6
            ">

                <button
                    disabled={page === 0}
                    onClick={() =>
                        setPage(prev => prev - 1)
                    }
                    className="
                        px-4 py-2
                        bg-[#111]
                        rounded-xl
                        disabled:opacity-40
                    "
                >
                    Prev
                </button>

                <span>
                    Page {page + 1} of {totalPages}
                </span>

                <button
                    disabled={page + 1 >= totalPages}
                    onClick={() =>
                        setPage(prev => prev + 1)
                    }
                    className="
                        px-4 py-2
                        bg-[#111]
                        rounded-xl
                        disabled:opacity-40
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
                                Create Product
                            </h2>

                            <button
                                onClick={() =>
                                    setShowModal(false)
                                }
                            >
                                <X />
                            </button>

                        </div>

                        <div className="
                            grid grid-cols-2
                            gap-4
                        ">

                            <input
                                placeholder="Product Name"
                                value={formData.productName}
                                onChange={(e) =>
                                    setFormData({
                                        ...formData,
                                        productName:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                "
                            />

                            <input
                                placeholder="Product Code"
                                value={formData.productCode}
                                onChange={(e) =>
                                    setFormData({
                                        ...formData,
                                        productCode:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                "
                            />

                            <input
                                placeholder="SKU"
                                value={formData.sku}
                                onChange={(e) =>
                                    setFormData({
                                        ...formData,
                                        sku:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                "
                            />

                            {/* <input
                                placeholder="Manufacturer"
                                value={formData.manufacturer}
                                onChange={(e) =>
                                    setFormData({
                                        ...formData,
                                        manufacturer:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                "
                            /> */}

                            <input
                                type="number"
                                placeholder="Price"
                                value={formData.price}
                                onChange={(e) =>
                                    setFormData({
                                        ...formData,
                                        price:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                "
                            />

                            <input
                                type="number"
                                placeholder="Stock Quantity"
                                value={formData.stockQuantity}
                                onChange={(e) =>
                                    setFormData({
                                        ...formData,
                                        stockQuantity:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                "
                            />

                            <select
                                value={formData.categoryId}
                                onChange={(e) =>
                                    setFormData({
                                        ...formData,
                                        categoryId:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                    col-span-2
                                "
                            >

                                <option value="">
                                    Select Category
                                </option>

                                {categories.map(category => (

                                    <option
                                        key={category.id}
                                        value={category.id}
                                    >
                                        {
                                            category.categoryName
                                        }
                                    </option>

                                ))}

                            </select>

                            <textarea
                                placeholder="Description"
                                value={formData.description}
                                onChange={(e) =>
                                    setFormData({
                                        ...formData,
                                        description:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                    col-span-2
                                    h-32
                                "
                            />

                        </div>

                        <div className="
                            flex justify-end
                            gap-3
                            mt-6
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
                                onClick={createProduct}
                                className="
                                    px-5 py-3
                                    rounded-xl
                                    bg-yellow-500
                                    text-black
                                    font-semibold
                                "
                            >
                                Create Product
                            </button>

                        </div>

                    </div>

                </div>
            )}

            {/* ========================================= */}
            {/* EDIT MODAL */}
            {/* ========================================= */}

            {showEditModal && selectedProduct && (

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
                                Edit Product
                            </h2>

                            <button
                                onClick={() => {

                                    setShowEditModal(false);

                                    setSelectedProduct(null);

                                }}
                            >
                                <X />
                            </button>

                        </div>

                        <div className="
                            grid grid-cols-2
                            gap-4
                        ">

                            <input
                                value={
                                    selectedProduct.productName
                                }
                                onChange={(e) =>
                                    setSelectedProduct({
                                        ...selectedProduct,
                                        productName:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                "
                            />

                            <input
                                value={
                                    selectedProduct.productCode
                                }
                                onChange={(e) =>
                                    setSelectedProduct({
                                        ...selectedProduct,
                                        productCode:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                "
                            />

                            <input
                                value={
                                    selectedProduct.sku
                                }
                                onChange={(e) =>
                                    setSelectedProduct({
                                        ...selectedProduct,
                                        sku:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                "
                            />

                            {/* <input
                                value={
                                    selectedProduct.manufacturer
                                }
                                onChange={(e) =>
                                    setSelectedProduct({
                                        ...selectedProduct,
                                        manufacturer:
                                            e.target.value,
                                    })
                                }
                                className="
                                    bg-[#1a1a1a]
                                    p-4
                                    rounded-xl
                                "
                            /> */}

                        </div>

                        <div className="
                            flex justify-end
                            gap-3
                            mt-6
                        ">

                            <button
                                onClick={() => {

                                    setShowEditModal(false);

                                    setSelectedProduct(null);

                                }}
                                className="
                                    px-5 py-3
                                    rounded-xl
                                    bg-[#222]
                                "
                            >
                                Cancel
                            </button>

                            <button
                                onClick={updateProduct}
                                className="
                                    px-5 py-3
                                    rounded-xl
                                    bg-yellow-500
                                    text-black
                                    font-semibold
                                "
                            >
                                Save Changes
                            </button>

                        </div>

                    </div>

                </div>
            )}

        </div>
    );
}