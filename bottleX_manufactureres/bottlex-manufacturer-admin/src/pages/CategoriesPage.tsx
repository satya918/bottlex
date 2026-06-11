import {
    useEffect,
    useState,
} from "react";

import {
    Plus,
    Search,
    Pencil,
    Trash2,
    Power,
    Layers3,
    CheckCircle,
    XCircle,
    X,
    ArrowLeft,

} from "lucide-react";
import { useNavigate } from "react-router-dom";


import toast from "react-hot-toast";

import { CategoriesAPI } from "../api/categories.api";

interface Category {

    id: string;

    categoryName: string;

    categoryCode: string;

    description: string;

    active: boolean;
}

export default function CategoriesPage() {
    const navigate = useNavigate();

    // =========================================
    // STATE
    // =========================================

    const [categories, setCategories] =
        useState<Category[]>([]);

    const [loading, setLoading] =
        useState(true);

    const [search, setSearch] =
        useState("");

    const [showModal, setShowModal] =
        useState(false);

    const [showEditModal, setShowEditModal] =
        useState(false);

    const [selectedCategory, setSelectedCategory] =
        useState<Category | null>(null);

    const [formData, setFormData] =
        useState({
            categoryName: "",
            categoryCode: "",
            description: "",
        });

    // =========================================
    // FETCH CATEGORIES
    // =========================================

    const fetchCategories = async () => {

        try {

            setLoading(true);

            const response =
                await CategoriesAPI.getCategories(
                );

            setCategories(response.data);

        } catch (error) {

            console.log(error);

            toast.error(
                "Failed to fetch categories"
            );

        } finally {

            setLoading(false);
        }
    };

    useEffect(() => {

        fetchCategories();

    }, []);

    // =========================================
    // CREATE CATEGORY
    // =========================================

    const createCategory = async () => {

        try {

            if (
                !formData.categoryName ||
                !formData.categoryCode
            ) {

                toast.error(
                    "Please fill all required fields"
                );

                return;
            }

            await CategoriesAPI.createCategory(formData);

            toast.success(
                "Category created successfully"
            );

            setShowModal(false);

            setFormData({
                categoryName: "",
                categoryCode: "",
                description: "",
            });

            fetchCategories();

        } catch (error) {

            console.log(error);

            toast.error(
                "Failed to create category"
            );
        }
    };

    // =========================================
    // UPDATE CATEGORY
    // =========================================

    const updateCategory = async () => {

        try {

            await CategoriesAPI.updateCategory(
                selectedCategory!.id,
                selectedCategory
            );


            toast.success(
                "Category updated successfully"
            );

            setShowEditModal(false);

            setSelectedCategory(null);

            fetchCategories();

        } catch (error) {

            console.log(error);

            toast.error(
                "Failed to update category"
            );
        }
    };

    // =========================================
    // DELETE CATEGORY
    // =========================================

    const deleteCategory = async (
        id: string
    ) => {

        const confirmed =
            window.confirm(
                "Are you sure you want to delete this category?"
            );

        if (!confirmed) return;

        try {

            await CategoriesAPI.deleteCategory(id);

            toast.success(
                "Category deleted successfully"
            );

            fetchCategories();

        } catch (error) {

            console.log(error);

            toast.error(
                "Failed to delete category"
            );
        }
    };

    // =========================================
    // TOGGLE STATUS
    // =========================================

    const toggleStatus = async (
        category: Category
    ) => {

        try {

            await CategoriesAPI.toggleStatus(category.id, !category.active);

            toast.success(
                "Status updated successfully"
            );

            fetchCategories();

        } catch (error) {

            console.log(error);

            toast.error(
                "Failed to update status"
            );
        }
    };

    // =========================================
    // FILTERED DATA
    // =========================================

    const filteredCategories =
        categories.filter((category) =>
            category.categoryName
                .toLowerCase()
                .includes(
                    search.toLowerCase()
                )
        );

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
                items-center
                justify-between
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
                        Categories
                    </h1>

                    <p className="
                        text-gray-400
                        mt-2
                    ">
                        Manage product categories
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

                    Add Category

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
                        flex justify-between
                        items-center
                    ">

                        <div>

                            <p className="text-gray-400">
                                Total Categories
                            </p>

                            <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                                {categories.length}
                            </h2>

                        </div>

                        <Layers3
                            className="
                                text-yellow-500
                            "
                            size={40}
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

                            <p className="text-gray-400">
                                Active Categories
                            </p>

                            <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                                {
                                    categories.filter(
                                        c => c.active
                                    ).length
                                }
                            </h2>

                        </div>

                        <CheckCircle
                            className="
                                text-green-500
                            "
                            size={40}
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

                            <p className="text-gray-400">
                                Inactive Categories
                            </p>

                            <h2 className="
                                text-3xl
                                font-bold
                                mt-2
                            ">
                                {
                                    categories.filter(
                                        c => !c.active
                                    ).length
                                }
                            </h2>

                        </div>

                        <XCircle
                            className="
                                text-red-500
                            "
                            size={40}
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
                mb-6
                flex items-center gap-3
            ">

                <Search
                    className="
                        text-gray-500
                    "
                    size={20}
                />

                <input
                    type="text"
                    placeholder="Search categories..."
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

                    <thead className="bg-[#181818]">

                        <tr className="text-gray-400">

                            <th className="
                                text-left
                                p-5
                            ">
                                Category
                            </th>

                            <th className="
                                text-left
                                p-5
                            ">
                                Code
                            </th>

                            <th className="
                                text-left
                                p-5
                            ">
                                Description
                            </th>

                            <th className="
                                text-left
                                p-5
                            ">
                                Status
                            </th>

                            <th className="
                                text-left
                                p-5
                            ">
                                Actions
                            </th>

                        </tr>

                    </thead>

                    <tbody>

                        {loading ? (

                            <tr>

                                <td
                                    colSpan={5}
                                    className="
                                        text-center
                                        p-10
                                        text-gray-400
                                    "
                                >
                                    Loading categories...
                                </td>

                            </tr>

                        ) : filteredCategories.length === 0 ? (

                            <tr>

                                <td
                                    colSpan={5}
                                    className="
                                        text-center
                                        p-10
                                        text-gray-500
                                    "
                                >
                                    No Active Categories found
                                </td>

                            </tr>

                        ) : (

                            filteredCategories.map(
                                (category) => (

                                    <tr
                                        key={category.id}
                                        className="
                                            border-t
                                            border-[#222]
                                            hover:bg-[#181818]
                                            transition
                                        "
                                    >

                                        <td className="p-5">

                                            <p className="
                                                font-semibold
                                            ">
                                                {
                                                    category.categoryName
                                                }
                                            </p>

                                        </td>

                                        <td className="p-5">

                                            {
                                                category.categoryCode
                                            }

                                        </td>

                                        <td className="
                                            p-5
                                            text-gray-400
                                        ">

                                            {
                                                category.description
                                            }

                                        </td>

                                        <td className="p-5">

                                            {category.active ? (

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

                                                        setSelectedCategory(
                                                            category
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
                                                        deleteCategory(
                                                            category.id
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
                                                            category
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
                                )
                            )
                        )}

                    </tbody>

                </table>

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
                                Create Category
                            </h2>

                            <button
                                onClick={() =>
                                    setShowModal(false)
                                }
                                className="
                                    text-gray-400
                                    hover:text-white
                                "
                            >
                                <X />
                            </button>

                        </div>

                        <div className="
                            grid grid-cols-1
                            gap-4
                        ">

                            <input
                                placeholder="Category Name"
                                value={formData.categoryName}
                                onChange={(e) =>
                                    setFormData({
                                        ...formData,
                                        categoryName:
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
                                placeholder="Category Code"
                                value={formData.categoryCode}
                                onChange={(e) =>
                                    setFormData({
                                        ...formData,
                                        categoryCode:
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
                                    border border-[#333]
                                    rounded-xl
                                    p-4
                                    outline-none
                                    h-32
                                "
                            />

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
                                onClick={createCategory}
                                className="
                                    px-5 py-3
                                    rounded-xl
                                    bg-yellow-500
                                    hover:bg-yellow-400
                                    text-black
                                    font-semibold
                                "
                            >
                                Create Category
                            </button>

                        </div>

                    </div>

                </div>
            )}

            {/* ========================================= */}
            {/* EDIT MODAL */}
            {/* ========================================= */}

            {showEditModal &&
                selectedCategory && (

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
                                    Edit Category
                                </h2>

                                <button
                                    onClick={() => {

                                        setShowEditModal(
                                            false
                                        );

                                        setSelectedCategory(
                                            null
                                        );

                                    }}
                                    className="
                                        text-gray-400
                                        hover:text-white
                                    "
                                >
                                    <X />
                                </button>

                            </div>

                            <div className="
                                grid grid-cols-1
                                gap-4
                            ">

                                <input
                                    value={
                                        selectedCategory.categoryName
                                    }
                                    onChange={(e) =>
                                        setSelectedCategory({
                                            ...selectedCategory,
                                            categoryName:
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
                                    value={
                                        selectedCategory.categoryCode
                                    }
                                    onChange={(e) =>
                                        setSelectedCategory({
                                            ...selectedCategory,
                                            categoryCode:
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

                                <textarea
                                    value={
                                        selectedCategory.description
                                    }
                                    onChange={(e) =>
                                        setSelectedCategory({
                                            ...selectedCategory,
                                            description:
                                                e.target.value,
                                        })
                                    }
                                    className="
                                        bg-[#1a1a1a]
                                        border border-[#333]
                                        rounded-xl
                                        p-4
                                        outline-none
                                        h-32
                                    "
                                />

                            </div>

                            <div className="
                                flex justify-end
                                gap-4
                                mt-8
                            ">

                                <button
                                    onClick={() => {

                                        setShowEditModal(
                                            false
                                        );

                                        setSelectedCategory(
                                            null
                                        );

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
                                    onClick={updateCategory}
                                    className="
                                        px-5 py-3
                                        rounded-xl
                                        bg-yellow-500
                                        hover:bg-yellow-400
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