import {
  LayoutDashboard,
  Package,
  Boxes,
  ShieldAlert,
  QrCode,
  Recycle,
  Users,
  Settings,
  LogOut,
} from "lucide-react";

import {
  NavLink,
} from "react-router-dom";

const menus = [
  {
    name: "Dashboard",
    icon: LayoutDashboard,
    path: "/dashboard",
  },
  {
    name: "Products",
    icon: Package,
    path: "/products",
  },
  {
    name: "Batches",
    icon: Boxes,
    path: "/batches",
  },
  {
    name: "QR Management",
    icon: QrCode,
    path: "/qr-management",
  },
  {
    name: "Counterfeit",
    icon: ShieldAlert,
    path: "/counterfeit",
  },
  {
    name: "Recycling",
    icon: Recycle,
    path: "/recycling",
  },
  {
    name: "Users",
    icon: Users,
    path: "/users",
  },
  {
    name: "Settings",
    icon: Settings,
    path: "/settings",
  },
];

export default function Sidebar() {
  return (
    <div className="w-72 min-h-screen bg-zinc-950 border-r border-zinc-800 flex flex-col">
      {/* LOGO */}

      <div className="p-8 border-b border-zinc-800">
        <h1 className="text-3xl font-bold text-yellow-400">
          BottleX
        </h1>

        <p className="text-gray-500 text-sm mt-2">
          Manufacturer Portal
        </p>
      </div>

      {/* MENU */}

      <div className="flex-1 p-5">
        <div className="space-y-2">
          {menus.map((menu) => {
            const Icon = menu.icon;

            return (
              <NavLink
                key={menu.name}
                to={menu.path}
                className={({ isActive }) =>
                  `flex items-center gap-4 px-5 py-4 rounded-2xl transition-all ${
                    isActive
                      ? "bg-yellow-500 text-black font-semibold"
                      : "text-gray-300 hover:bg-zinc-900"
                  }`
                }
              >
                <Icon size={22} />

                <span className="text-[15px]">
                  {menu.name}
                </span>
              </NavLink>
            );
          })}
        </div>
      </div>

      {/* FOOTER */}

      <div className="p-5 border-t border-zinc-800">
        <button className="w-full flex items-center gap-4 px-5 py-4 rounded-2xl text-red-400 hover:bg-red-500/10 transition-all">
          <LogOut size={22} />

          <span className="font-medium">
            Logout
          </span>
        </button>
      </div>
    </div>
  );
}