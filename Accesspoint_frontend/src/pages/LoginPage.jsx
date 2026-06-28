import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { login } from '../api/api';
import { useAuth } from '../context/AuthContext';

export default function LoginPage() {
  const navigate = useNavigate();
  const { checkAuth } = useAuth();
  const [form, setForm]       = useState({ email: '', password: '' });
  const [loading, setLoading] = useState(false);

  const handle = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const submit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await login(form.email, form.password);
      await checkAuth();           // refresh user in context
      toast.success('Welcome back!');
      navigate('/profile');
    } catch (err) {
      const msg = err.response?.data?.message || 'Login failed';
      toast.error(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-wrap">
      <div className="auth-left">
        <div className="auth-left-bg" /><div className="auth-left-grid" />
        <div className="auth-left-body">
          <div className="auth-left-logo">Access<em>Point</em></div>
          <h2 className="auth-left-h">Welcome<br />back.</h2>
          <p className="auth-left-p">
            Sign in to your account. Your JWT is stored in a secure HttpOnly
            cookie — no local-storage risks.
          </p>
          <div className="auth-chips">
            <span className="chip"><span className="chip-dot"/>HttpOnly cookie</span>
            <span className="chip"><span className="chip-dot"/>1-day session</span>
            <span className="chip"><span className="chip-dot"/>BCrypt secured</span>
          </div>
        </div>
      </div>

      <div className="auth-right">
        <div className="auth-card anim">
          <h1 className="auth-title">Sign in</h1>
          <p className="auth-sub">Enter your credentials to continue.</p>

          <form onSubmit={submit}>
            <div className="fg">
              <label className="fl">Email</label>
              <input className="fi" name="email" type="email" placeholder="you@example.com"
                value={form.email} onChange={handle} required />
            </div>
            <div className="fg">
              <label className="fl">Password</label>
              <input className="fi" name="password" type="password" placeholder="••••••••"
                value={form.password} onChange={handle} required />
            </div>
            <div className="tc mt3">
              <Link to="/forgot-password" className="a-link">Forgot password?</Link>
            </div>
            <button className="btn-main" disabled={loading}>
              {loading ? <span className="spin"/> : 'Sign In'}
            </button>
          </form>

          <div className="divider">or</div>
          <p className="tc dim">No account yet?{' '}
            <Link to="/register" className="a-link">Create one</Link>
          </p>
        </div>
      </div>
    </div>
  );
}
