import { useState } from "react";

import { ShieldCheck } from "lucide-react";

import { useNavigate } from "react-router-dom";

import apiClient from "../api/apiClient"; // adjust path

export default function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");

  const [password, setPassword] = useState("");

  const [loading, setLoading] = useState(false);

  const handleLogin = async (
    e: React.FormEvent
  ) => {
    e.preventDefault();

    if (!email || !password) {
      alert("Enter email & password");
      return;
    }

    try {
      setLoading(true);

      const response = await apiClient.post(
        "/api/auth/login",
        {
          userName: email, // backend expects userName
          password: password,
        }
      );

      console.log(response.data);

      // STORE ACCESS TOKEN
      localStorage.setItem(
        "manufacturer_token",
        response.data.accessToken
      );

      // STORE USER DATA (optional)
      localStorage.setItem(
        "user",
        JSON.stringify(response.data.user)
      );

      navigate("/dashboard");

    } catch (error: any) {
      console.log(error);

      if (error.response?.data?.message) {
        alert(error.response.data.message);
      } else {
        alert("Login failed");
      }

    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-black flex items-center justify-center px-5">
      <div className="w-full max-w-md bg-zinc-950 border border-zinc-800 rounded-3xl p-10">
        
        {/* LOGO */}
        <div className="flex flex-col items-center">
          <div className="w-20 h-20 rounded-3xl bg-yellow-500 flex items-center justify-center">
            <ShieldCheck
              size={40}
              className="text-black"
            />
          </div>

          <h1 className="text-4xl font-bold text-white mt-6">
            Bottlex
          </h1>

          <p className="text-gray-400 mt-3 text-center">
            Manufacturer Admin Portal
          </p>
        </div>

        {/* FORM */}
        <form
          onSubmit={handleLogin}
          className="mt-10"
        >

          {/* EMAIL */}
          <div className="mb-5">
            <label className="text-sm text-gray-400 block mb-3">
              Company Email
            </label>

            <input
              type="email"
              placeholder="admin@company.com"
              value={email}
              onChange={(e) =>
                setEmail(e.target.value)
              }
              className="w-full h-14 bg-zinc-900 border border-zinc-800 rounded-2xl px-5 text-white outline-none focus:border-yellow-500"
            />
          </div>

          {/* PASSWORD */}
          <div className="mb-7">
            <label className="text-sm text-gray-400 block mb-3">
              Password
            </label>

            <input
              type="password"
              placeholder="Enter password"
              value={password}
              onChange={(e) =>
                setPassword(e.target.value)
              }
              className="w-full h-14 bg-zinc-900 border border-zinc-800 rounded-2xl px-5 text-white outline-none focus:border-yellow-500"
            />
          </div>

          {/* BUTTON */}
          <button
            type="submit"
            disabled={loading}
            className="w-full h-14 bg-yellow-500 hover:bg-yellow-400 transition-all rounded-2xl text-black font-bold text-lg"
          >
            {loading
              ? "Signing In..."
              : "Login"}
          </button>
        </form>

        {/* FOOTER */}
        <div className="mt-8 text-center">
          <p className="text-gray-500 text-sm">
            Enterprise Bottle Authentication Platform
          </p>
        </div>
      </div>
    </div>
  );
}