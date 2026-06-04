import {
    useEffect,
    useState,
} from 'react';

import {
    Search,
    Plus,
    Users,
    ShieldCheck,
    UserCheck,
    ArrowLeft,

} from 'lucide-react';
import { useNavigate } from "react-router-dom";

import toast from 'react-hot-toast';

import apiClient from '../../api/apiClient';

export default function UsersPage() {

    const navigate = useNavigate();

    interface Permission {
        id: string;
        permissionName: string;
    }

    interface Role {
        id: string;
        roleName: string;
        permissions: Permission[];
    }

    interface Company {
        id: string;
        companyName: string;
        companyCode: string;
    }

    interface User {
        id: string;
        firstName: string;
        lastName: string;
        userName: string;
        email: string;
        active: boolean;
        company: Company;
        roles: Role[];
    }
    const [matrix, setMatrix] = useState<any>(null);

    const [page, setPage] = useState(0);

    const [totalPages, setTotalPages] =
        useState(0);
    const [users, setUsers] = useState<User[]>([]);

    const [showModal, setShowModal] = useState(false);

    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        userName: '',
        phone: '',
        password: '',
        roleIds: [] as string[],
    });

    const [roles, setRoles] = useState<any[]>([]);

    const [loading, setLoading] =
        useState(true);

    const [search, setSearch] =
        useState('');

    const [selectedUser, setSelectedUser] =
        useState<any>(null);

    const [showEditModal, setShowEditModal] =
        useState(false);

    const fetchUsers = async () => {
        try {
            const response = await apiClient.get(
                `/api/admin/users?page=${page}&size=10`
            );

            setUsers(response.data.content); // FIX
            setTotalPages(response.data.totalPages); // FIX

        } catch (error) {
            console.log(error);
        } finally {
            setLoading(false);
        }
    };
    const fetchRoles = async () => {

        try {

            const response =
                await apiClient.get('/api/admin/users/roles');

            setRoles(response.data);

        } catch (error) {

            console.log(error);
        }
    };
    const createUser = async () => {

        try {

            await apiClient.post(
                '/api/admin/users',
                formData
            );

            setShowModal(false);

            setFormData({
                firstName: '',
                lastName: '',
                email: '',
                userName: '',
                phone: '',
                password: '',
                roleIds: [],
            });

            fetchUsers();

        } catch (error) {

            console.log(error);
        }
    };
    useEffect(() => {
        fetchUsers();
        fetchRoles();
    }, [page]);

    useEffect(() => {
        fetchMatrix();
    }, []);

    const fetchMatrix = async () => {
        try {
            const res = await apiClient.get('/api/admin/users/permissions/matrix');
            setMatrix(res.data);
        } catch (error) {
            console.log(error);
        }
    };

    const handleToggle = async (role: string, permission: string) => {
        try {
            await apiClient.patch(
                `/api/admin/users/roles/${role}/permissions`,
                { permission }
            );

            await fetchMatrix(); // FIXED
        } catch (err) {
            console.log(err);
        }
    };

    const filteredUsers =
        users.filter((user) =>
            `${user.firstName} ${user.lastName}`
                .toLowerCase()
                .includes(search.toLowerCase())
        );

    return (

        <div className="min-h-screen bg-black text-white p-8">

            {/* HEADER */}

            <div className="flex items-center justify-between mb-8">

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
                    <h1 className="text-4xl font-bold">
                        User Management
                    </h1>

                    <p className="text-gray-400 mt-2">
                        Manage manufacturer users,
                        permissions and access
                    </p>

                </div>

                <button
                    onClick={() => setShowModal(true)}
                    className="
        flex items-center gap-2
        bg-yellow-500
        hover:bg-yellow-400
        text-black
        font-semibold
        px-5 py-3
        rounded-2xl
        transition
    "
                >
                    <Plus size={18} />
                    Create User
                </button>

            </div>

            {/* STATS */}

            <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">

                <div className="
                    bg-[#111]
                    border border-[#222]
                    rounded-3xl
                    p-6
                ">

                    <div className="flex justify-between items-center">

                        <div>

                            <p className="text-gray-400">
                                Total Users
                            </p>

                            <h2 className="text-3xl font-bold mt-2">
                                {users.length}
                            </h2>

                        </div>

                        <Users
                            size={40}
                            className="text-yellow-500"
                        />

                    </div>

                </div>

                <div className="
                    bg-[#111]
                    border border-[#222]
                    rounded-3xl
                    p-6
                ">

                    <div className="flex justify-between items-center">

                        <div>

                            <p className="text-gray-400">
                                Active Users
                            </p>

                            <h2 className="text-3xl font-bold mt-2">
                                {
                                    users.filter(
                                        u => u.active
                                    ).length
                                }
                            </h2>

                        </div>

                        <UserCheck
                            size={40}
                            className="text-green-500"
                        />

                    </div>

                </div>

                <div className="
                    bg-[#111]
                    border border-[#222]
                    rounded-3xl
                    p-6
                ">

                    <div className="flex justify-between items-center">

                        <div>

                            <p className="text-gray-400">
                                Roles
                            </p>

                            <h2 className="text-3xl font-bold mt-2">
                                {
                                    [...new Set(
                                        users.flatMap(user =>
                                            user.roles?.map((role: any) => role.roleName) || []
                                        )
                                    )].length
                                }
                            </h2>

                        </div>

                        <ShieldCheck
                            size={40}
                            className="text-blue-500"
                        />

                    </div>

                </div>

            </div>

            {/* SEARCH */}

            <div className="
                bg-[#111]
                border border-[#222]
                rounded-2xl
                p-4
                mb-6
                flex items-center
                gap-3
            ">

                <Search
                    size={20}
                    className="text-gray-500"
                />

                <input
                    type="text"
                    placeholder="Search users..."
                    value={search}
                    onChange={(e) =>
                        setSearch(e.target.value)
                    }
                    className="
                        bg-transparent
                        outline-none
                        flex-1
                        text-white
                        placeholder:text-gray-500
                    "
                />

            </div>

            {/* TABLE */}

            <div className="
                bg-[#111]
                border border-[#222]
                rounded-3xl
                overflow-hidden
            ">

                <table className="w-full">

                    <thead className="bg-[#181818]">

                        <tr className="text-gray-400">

                            <th className="text-left p-5">
                                User
                            </th>

                            <th className="text-left p-5">
                                Email
                            </th>

                            <th className="text-left p-5">
                                Roles
                            </th>

                            <th className="text-left p-5">
                                Status
                            </th>

                            <th className="text-left p-5">
                                Actions
                            </th>

                        </tr>

                    </thead>

                    <tbody>

                        {loading ? (

                            <tr>

                                <td
                                    colSpan={5}
                                    className="text-center p-10 text-gray-400"
                                >
                                    Loading users...
                                </td>

                            </tr>

                        ) : filteredUsers.length === 0 ? (

                            <tr>

                                <td
                                    colSpan={5}
                                    className="text-center p-10 text-gray-500"
                                >
                                    No users found
                                </td>

                            </tr>

                        ) : (

                            filteredUsers.map((user) => (

                                <tr
                                    key={user.id}
                                    className="
                                        border-t border-[#222]
                                        hover:bg-[#181818]
                                        transition
                                    "
                                >

                                    {/* USER */}

                                    <td className="p-5">

                                        <div className="flex items-center gap-4">

                                            <div className="
                                                w-12 h-12
                                                rounded-full
                                                bg-yellow-500
                                                text-black
                                                font-bold
                                                flex items-center justify-center
                                            ">

                                                {user.firstName?.charAt(0)}

                                            </div>

                                            <div>

                                                <p className="font-semibold">
                                                    {user.firstName}
                                                    {' '}
                                                    {user.lastName}
                                                </p>

                                                <p className="text-sm text-gray-500">
                                                    @{user.userName}
                                                </p>

                                            </div>

                                        </div>

                                    </td>

                                    {/* EMAIL */}

                                    <td className="p-5 text-gray-300">

                                        {user.email}

                                    </td>

                                    {/* ROLES */}

                                    <td className="p-5">

                                        <div className="flex gap-2 flex-wrap">

                                            {user.roles?.map(
                                                (role: any) => (

                                                    <span
                                                        key={role.id}
                                                        className="
                                                            bg-yellow-500/10
                                                            text-yellow-400
                                                            border border-yellow-500/20
                                                            text-xs
                                                            px-3 py-1
                                                            rounded-full
                                                        "
                                                    >
                                                        {role.roleName}
                                                    </span>
                                                )
                                            )}

                                        </div>

                                    </td>

                                    {/* STATUS */}

                                    <td className="p-5">

                                        {user.active ? (

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

                                    {/* ACTIONS */}

                                    <td className="p-5">

                                        <div className="flex gap-3">

                                            <button
                                                onClick={() => {

                                                    setSelectedUser(user);
                                                    setShowEditModal(true);

                                                }}
                                                className="
                                                            bg-white/10
                                                             hover:bg-white/20
                                                            px-4 py-2
                                                            rounded-xl
                                                            text-sm
                                                            transition
                                                            "
                                            >
                                                Edit
                                            </button>

                                            <button
                                                onClick={async () => {

                                                    try {

                                                        await apiClient.patch(
                                                            `/api/admin/users/${user.id}/status?active=${!user.active}`
                                                        );
                                                        toast.success(
                                                            user.active
                                                                ? 'User disabled'
                                                                : 'User activated'
                                                        );
                                                        fetchUsers();

                                                    } catch (error) {
                                                        toast.error('Operation failed');

                                                        console.log(error);
                                                    }

                                                }}
                                                className={`
        px-4 py-2
        rounded-xl
        text-sm
        transition

        ${user.active
                                                        ? `
                bg-red-500/10
                hover:bg-red-500/20
                text-red-400
              `
                                                        : `
                bg-green-500/10
                hover:bg-green-500/20
                text-green-400
              `
                                                    }
    `}
                                            >
                                                {user.active ? 'Disable' : 'Activate'}
                                            </button>

                                        </div>

                                    </td>

                                </tr>
                            ))
                        )}

                    </tbody>

                </table>

            </div>
            <div className="flex justify-center gap-4 mt-6">

                <button
                    disabled={page === 0}
                    onClick={() => setPage(page - 1)}
                    className="
            px-4 py-2
            rounded-xl
            bg-white/10
            disabled:opacity-30
        "
                >
                    Previous
                </button>

                <span className="flex items-center">
                    Page {page + 1} of {totalPages}
                </span>

                <button
                    disabled={page + 1 >= totalPages}
                    onClick={() => setPage(page + 1)}
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

            {/* PERMISSION MATRIX */}
            <div className="overflow-x-auto bg-[#111] border border-[#222] rounded-3xl p-6 mt-8">
                {matrix && (
                    <table className="w-full border-collapse">
                        <thead>
                            <tr className="text-left text-gray-400">
                                <th className="p-4">Role</th>

                                {matrix.permissions.map((perm: string) => (
                                    <th key={perm} className="p-4 text-center">
                                        {perm}
                                    </th>
                                ))}
                            </tr>
                        </thead>

                        <tbody>
                            {matrix.roles.map((role: string) => (
                                <tr key={role} className="border-t border-[#222]">
                                    <td className="p-4 font-semibold text-white">
                                        {role}
                                    </td>

                                    {matrix.permissions.map((perm: string) => {
                                        const hasPermission =
                                            matrix.rolePermissions[role]?.includes(perm);

                                        return (
                                            <td key={perm} className="text-center p-4">
                                                {hasPermission ? (
                                                    <span className="text-green-400 text-xl">✔</span>
                                                ) : (
                                                    <span className="text-red-500/40 text-xl">✖</span>
                                                )}
                                            </td>
                                        );
                                    })}
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}
            </div>
            {
                showModal && (

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

                            <h2 className="text-2xl font-bold mb-6">
                                Create User
                            </h2>

                            <div className="grid grid-cols-2 gap-4">

                                <input
                                    placeholder="First Name"
                                    value={formData.firstName}
                                    onChange={(e) =>
                                        setFormData({
                                            ...formData,
                                            firstName: e.target.value
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
                                    placeholder="Last Name"
                                    value={formData.lastName}
                                    onChange={(e) =>
                                        setFormData({
                                            ...formData,
                                            lastName: e.target.value
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
                                    placeholder="Email"
                                    value={formData.email}
                                    onChange={(e) =>
                                        setFormData({
                                            ...formData,
                                            email: e.target.value
                                        })
                                    }
                                    className="
                            bg-[#1a1a1a]
                            border border-[#333]
                            rounded-xl
                            p-4
                            outline-none
                            col-span-2
                        "
                                />

                                <input
                                    placeholder="Username"
                                    value={formData.userName}
                                    onChange={(e) =>
                                        setFormData({
                                            ...formData,
                                            userName: e.target.value
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
                                    placeholder="Phone"
                                    value={formData.phone}
                                    onChange={(e) =>
                                        setFormData({
                                            ...formData,
                                            phone: e.target.value
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
                                    type="password"
                                    placeholder="Password"
                                    value={formData.password}
                                    onChange={(e) =>
                                        setFormData({
                                            ...formData,
                                            password: e.target.value
                                        })
                                    }
                                    className="
                            bg-[#1a1a1a]
                            border border-[#333]
                            rounded-xl
                            p-4
                            outline-none
                            col-span-2
                        "
                                />

                                {/* ROLES */}

                                <div className="col-span-2">

                                    <p className="mb-3 text-gray-400">
                                        Assign Roles
                                    </p>

                                    <div className="flex gap-3 flex-wrap">

                                        {roles.map((role) => {

                                            const selected =
                                                formData.roleIds.includes(role.id);

                                            return (

                                                <button
                                                    key={role.id}
                                                    onClick={() => {

                                                        if (selected) {

                                                            setFormData({
                                                                ...formData,
                                                                roleIds:
                                                                    formData.roleIds.filter(
                                                                        id => id !== role.id
                                                                    )
                                                            });

                                                        } else {

                                                            setFormData({
                                                                ...formData,
                                                                roleIds: [
                                                                    ...formData.roleIds,
                                                                    role.id
                                                                ]
                                                            });
                                                        }
                                                    }}
                                                    className={`
                                            px-4 py-2 rounded-full text-sm transition
                                            ${selected
                                                            ? 'bg-yellow-500 text-black'
                                                            : 'bg-[#222] text-white'}
                                        `}
                                                >
                                                    {role.roleName}
                                                </button>
                                            );
                                        })}

                                    </div>

                                </div>

                            </div>

                            {/* ACTIONS */}

                            <div className="flex justify-end gap-4 mt-8">

                                <button
                                    onClick={() => setShowModal(false)}
                                    className="
                            px-5 py-3
                            rounded-xl
                            bg-[#222]
                            hover:bg-[#333]
                        "
                                >
                                    Cancel
                                </button>

                                <button
                                    onClick={createUser}
                                    className="
                            px-5 py-3
                            rounded-xl
                            bg-yellow-500
                            hover:bg-yellow-400
                            text-black
                            font-semibold
                        "
                                >
                                    Create User
                                </button>

                            </div>

                        </div>

                    </div>
                )
            }

            {
                showEditModal && selectedUser && (

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

                            <div className="flex justify-between items-center mb-6">

                                <h2 className="text-2xl font-bold">
                                    Edit User
                                </h2>

                                <button
                                    onClick={() => {

                                        setShowEditModal(false);
                                        setSelectedUser(null);

                                    }}
                                    className="text-gray-400 hover:text-white"
                                >
                                    ✕
                                </button>

                            </div>

                            {/* FIRST NAME */}

                            <div className="mb-4">

                                <label className="text-sm text-gray-400">
                                    First Name
                                </label>

                                <input
                                    type="text"
                                    value={selectedUser.firstName}
                                    onChange={(e) =>
                                        setSelectedUser({
                                            ...selectedUser,
                                            firstName: e.target.value
                                        })
                                    }
                                    className="
                            w-full mt-2
                            bg-[#181818]
                            border border-[#222]
                            rounded-xl
                            px-4 py-3
                            outline-none
                        "
                                />

                            </div>

                            {/* LAST NAME */}

                            <div className="mb-4">

                                <label className="text-sm text-gray-400">
                                    Last Name
                                </label>

                                <input
                                    type="text"
                                    value={selectedUser.lastName}
                                    onChange={(e) =>
                                        setSelectedUser({
                                            ...selectedUser,
                                            lastName: e.target.value
                                        })
                                    }
                                    className="
                            w-full mt-2
                            bg-[#181818]
                            border border-[#222]
                            rounded-xl
                            px-4 py-3
                            outline-none
                        "
                                />

                            </div>

                            {/* EMAIL */}

                            <div className="mb-6">

                                <label className="text-sm text-gray-400">
                                    Email
                                </label>

                                <input
                                    type="email"
                                    value={selectedUser.email}
                                    onChange={(e) =>
                                        setSelectedUser({
                                            ...selectedUser,
                                            email: e.target.value
                                        })
                                    }
                                    className="
                            w-full mt-2
                            bg-[#181818]
                            border border-[#222]
                            rounded-xl
                            px-4 py-3
                            outline-none
                        "
                                />

                            </div>

                            {/* ACTIONS */}

                            <div className="flex justify-end gap-3">

                                <button
                                    onClick={() => {

                                        setShowEditModal(false);
                                        setSelectedUser(null);

                                    }}
                                    className="
                            px-5 py-3
                            rounded-xl
                            bg-white/10
                            hover:bg-white/20
                        "
                                >
                                    Cancel
                                </button>

                                <button
                                    onClick={async () => {

                                        try {

                                            await apiClient.put(
                                                `/api/admin/users/${selectedUser.id}`,
                                                selectedUser
                                            );

                                            fetchUsers();

                                            setShowEditModal(false);
                                            setSelectedUser(null);

                                        } catch (error) {

                                            console.log(error);
                                        }

                                    }}
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
                )
            }
        </div>
    );
}