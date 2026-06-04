export default function PermissionMatrix({ matrix, onToggle }: any) {
    if (!matrix) return null;

    return (
        <table className="w-full">
            <thead>
                <tr>
                    <th>Role</th>
                    {matrix.permissions.map((p: string) => (
                        <th key={p}>{p}</th>
                    ))}
                </tr>
            </thead>

            <tbody>
                {matrix.roles.map((role: string) => (
                    <tr key={role}>
                        <td>{role}</td>

                        {matrix.permissions.map((perm: string) => (
                            <td key={perm}>
                                <button
                                    onClick={() => onToggle(role, perm)}
                                >
                                    {matrix.rolePermissions[role]?.includes(perm)
                                        ? "✔"
                                        : "✖"}
                                </button>
                            </td>
                        ))}
                    </tr>
                ))}
            </tbody>
        </table>
    );
}