import { useState } from 'react';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import { sendResetOtp } from '../api/api';

export default function ForgotPasswordPage() {
  const [email, setEmail]     = useState('');
  const [loading, setLoading] = useState(false);
  const [sent, setSent]       = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await sendResetOtp(email);
      setSent(true);
      toast.success('Reset OTP sent to your email!');
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to send OTP');
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
          <h2 className="auth-left-h">Forgot your<br />password?</h2>
          <p className="auth-left-p">
            No worries. Enter your email and we'll send a reset OTP instantly.
            Pick a new password on the next step.
          </p>
        </div>
      </div>

      <div className="auth-right">
        <div className="auth-card anim">
          {!sent ? (
            <>
              <h1 className="auth-title">Reset password</h1>
              <p className="auth-sub">We'll email you a one-time code to reset your password.</p>
              <form onSubmit={submit}>
                <div className="fg">
                  <label className="fl">Email address</label>
                  <input className="fi" type="email" placeholder="you@example.com"
                    value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                <button className="btn-main" disabled={loading}>
                  {loading ? <span className="spin"/> : 'Send Reset OTP'}
                </button>
              </form>
            </>
          ) : (
            <div style={{textAlign:'center', padding:'1rem 0'}}>
              <div style={{fontSize:'3rem', marginBottom:'1rem'}}>📬</div>
              <h2 className="auth-title">Check your inbox</h2>
              <p className="auth-sub" style={{marginBottom:'1.5rem'}}>
                OTP sent to <strong>{email}</strong>.<br />
                Use it to reset your password.
              </p>
              <Link to={`/reset-password?email=${encodeURIComponent(email)}`} className="btn-main" style={{display:'inline-flex', width:'100%', justifyContent:'center'}}>
                Enter OTP & Reset →
              </Link>
            </div>
          )}

          <div className="tc mt4">
            <Link to="/login" className="a-link">← Back to sign in</Link>
          </div>
        </div>
      </div>
    </div>
  );
}
