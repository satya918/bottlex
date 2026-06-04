import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";

import Dashboard from "./pages/Dashboard";
import Batches from "./pages/Batches";
import Counterfeit from "./pages/Counterfeit";
import Recycling from "./pages/Recycling";
import Settings from "./pages/Settings";
import QrManagement from "./pages/QrManagement";
import Login from "./pages/Login";

import ProtectedRoute from "./routes/ProtectedRoute";
import UsersPage from "./pages/users/UsersPage";
import ProductsPage from "./pages/ProductsPage";
import CategoriesPage from "./pages/CategoriesPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* LOGIN */}

        <Route
          path="/login"
          element={<Login />}
        />

        {/* PROTECTED ROUTES */}

         <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/"
          element={
            <ProtectedRoute>
              <Login />
            </ProtectedRoute>
          }
        />

        <Route
          path="/products"
          element={
            <ProtectedRoute>
              <ProductsPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="/categories"
          element={
            <ProtectedRoute>
              <CategoriesPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/batches"
          element={
            <ProtectedRoute>
              <Batches />
            </ProtectedRoute>
          }
        />

        <Route
          path="/counterfeit"
          element={
            <ProtectedRoute>
              <Counterfeit />
            </ProtectedRoute>
          }
        />

        <Route
          path="/recycling"
          element={
            <ProtectedRoute>
              <Recycling />
            </ProtectedRoute>
          }
        />

       
        <Route
          path="/users"
          element={<UsersPage />}
        />

        <Route
          path="/settings"
          element={
            <ProtectedRoute>
              <Settings />
            </ProtectedRoute>
          }
        />

        <Route
          path="/qr-management"
          element={
            <ProtectedRoute>
              <QrManagement />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;