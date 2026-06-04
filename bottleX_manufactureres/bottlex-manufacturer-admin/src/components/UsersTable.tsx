export default function UsersTable({
    users,
    loading,
    onEdit,
    onToggle,
}: any) {
    if (loading) return <p className="text-gray-400">Loading...</p>;

    return (
        <table className="w-full">
            <tbody>
                {users.map((user: any) => (
                    <tr key={user.id}>
                        <td>{user.firstName}</td>
                        <td>{user.email}</td>

                        <td>
                            <button onClick={() => onEdit(user)}>
                                Edit
                            </button>
                        </td>

                        <td>
                            <button onClick={() => onToggle(user)}>
                                {user.active ? "Disable" : "Enable"}
                            </button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
}