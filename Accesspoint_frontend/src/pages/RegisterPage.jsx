import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { register } from '../api/api';

export default function RegisterPage() {
  const navigate = useNavigate();
  const [form, setForm]     = useState({ name: '', email: '', password: '' });
  const [loading, setLoading] = useState(false);

  const handle = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const submit = async (e) => {
    e.preventDefault();
    if (form.password.length < 6 || form.password.length > 8) {
      toast.error('Password must be 6–8 characters');
      return;
    }
    setLoading(true);
    try {
      await register(form.name, form.email, form.password);
      toast.success('Account created! Please log in.');
      navigate('/login');
    } catch (err) {
      const msg = err.response?.data?.message || 'Registration failed';
      toast.error(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-wrap">
      {/* Left panel */}
      <div className="auth-left">
        <div className="auth-left-bg" /><div className="auth-left-grid" />
        <div className="auth-left-body">
          <div className="auth-left-logo">Access<em>Point</em></div>
          <h2 className="auth-left-h">Start your<br />journey here.</h2>
          <p className="auth-left-p">
            Create your account in seconds. We'll send a welcome email and
            let you verify your address with a one-time code.
          </p>
          <div className="auth-chips">
            <span className="chip"><span className="chip-dot"/>Free to use</span>
            <span className="chip"><span className="chip-dot"/>Email verified</span>
            <span className="chip"><span className="chip-dot"/>Secure by default</span>
          </div>
        </div>
      </div>

      {/* Right form */}
      <div className="auth-right">
        <div className="auth-card anim">
          <h1 className="auth-title">Create account</h1>
          <p className="auth-sub">Fill in the details below to get started.</p>

          <form onSubmit={submit}>
            <div className="fg">
              <label className="fl">Full Name</label>
              <input className="fi" name="name" placeholder="John Doe"
                value={form.name} onChange={handle} required />
            </div>
            <div className="fg">
              <label className="fl">Email</label>
              <input className="fi" name="email" type="email" placeholder="you@example.com"
                value={form.email} onChange={handle} required />
            </div>
            <div className="fg">
              <label className="fl">Password <span className="dim">(6–8 chars)</span></label>
              <input className="fi" name="password" type="password" placeholder="••••••••"
                value={form.password} onChange={handle} required />
            </div>
            <button className="btn-main" disabled={loading}>
              {loading ? <span className="spin"/> : 'Create Account'}
            </button>
          </form>

          <div className="divider">or</div>
          <p className="tc dim">Already have an account?{' '}
            <Link to="/login" className="a-link">Sign in</Link>
          </p>
        </div>
      </div>
    </div>
  );
}
