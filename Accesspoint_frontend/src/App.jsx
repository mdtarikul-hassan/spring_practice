import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './index.css';

import { AuthProvider, useAuth } from './context/AuthContext';
import Navbar          from './components/Navbar';
import HomePage        from './pages/HomePage';
import RegisterPage    from './pages/RegisterPage';
import LoginPage       from './pages/LoginPage';
import ProfilePage     from './pages/ProfilePage';
import VerifyEmailPage from './pages/VerifyEmailPage';
import ForgotPasswordPage from './pages/ForgotPasswordPage';
import ResetPasswordPage  from './pages/ResetPasswordPage';

// Guard: redirect to /login if not authenticated
function Protected({ children }) {
  const { user, loading } = useAuth();
  if (loading) return (
    <div style={{minHeight:'100vh', display:'flex', alignItems:'center', justifyContent:'center'}}>
      <span className="spin" style={{width:32, height:32, borderWidth:3}} />
    </div>
  );
  return user ? children : <Navigate to="/login" replace />;
}

function AppRoutes() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/"                 element={<HomePage />} />
        <Route path="/register"         element={<RegisterPage />} />
        <Route path="/login"            element={<LoginPage />} />
        <Route path="/forgot-password"  element={<ForgotPasswordPage />} />
        <Route path="/reset-password"   element={<ResetPasswordPage />} />
        <Route path="/profile"          element={<Protected><ProfilePage /></Protected>} />
        <Route path="/verify-email"     element={<Protected><VerifyEmailPage /></Protected>} />
        <Route path="*"                 element={<Navigate to="/" replace />} />
      </Routes>
      <ToastContainer
        position="top-right" autoClose={3500} theme="dark"
        toastStyle={{ background: '#0f1623', border: '1px solid #1c2d42', color: '#e1e8f0' }}
      />
    </>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>
    </BrowserRouter>
  );
}
