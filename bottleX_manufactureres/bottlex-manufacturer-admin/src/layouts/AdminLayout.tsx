import type { ReactNode } from "react";

import Sidebar from "../components/Sidebar";

type Props = {
  children: ReactNode;
};

export default function AdminLayout({
  children,
}: Props) {
  return (
    <div className="flex bg-black text-white">
      {/* SIDEBAR */}

      <Sidebar />

      {/* MAIN CONTENT */}

      <div className="flex-1 overflow-auto h-screen">
        {children}
      </div>
    </div>
  );
}