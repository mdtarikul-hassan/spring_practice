import { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { sendVerifyOtp, verifyOtp } from '../api/api';
import { useAuth } from '../context/AuthContext';

export default function VerifyEmailPage() {
  const navigate   = useNavigate();
  const { user, checkAuth } = useAuth();
  const [otp, setOtp]         = useState(Array(6).fill(''));
  const [sending, setSending]   = useState(false);
  const [verifying, setVerifying] = useState(false);
  const [sent, setSent]           = useState(false);
  const refs = useRef([]);

  const handleOtp = (idx, val) => {
    if (!/^\d?$/.test(val)) return;
    const next = [...otp];
    next[idx] = val;
    setOtp(next);
    if (val && idx < 5) refs.current[idx + 1]?.focus();
  };

  const handleKey = (idx, e) => {
    if (e.key === 'Backspace' && !otp[idx] && idx > 0) refs.current[idx - 1]?.focus();
  };

  const sendOtp = async () => {
    setSending(true);
    try {
      await sendVerifyOtp();
      setSent(true);
      toast.success('OTP sent to your email!');
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to send OTP');
    } finally {
      setSending(false);
    }
  };

  const verify = async (e) => {
    e.preventDefault();
    const code = otp.join('');
    if (code.length < 6) { toast.error('Enter all 6 digits'); return; }
    setVerifying(true);
    try {
      await verifyOtp(code);
      await checkAuth();
      toast.success('Email verified! 🎉');
      navigate('/profile');
    } catch (err) {
      toast.error(err.response?.data?.message || 'Invalid OTP');
    } finally {
      setVerifying(false);
    }
  };

  return (
    <div className="auth-wrap">
      <div className="auth-left">
        <div className="auth-left-bg" /><div className="auth-left-grid" />
        <div className="auth-left-body">
          <div className="auth-left-logo">Access<em>Point</em></div>
          <h2 className="auth-left-h">Verify your<br />email.</h2>
          <p className="auth-left-p">
            We send a 6-digit OTP to your registered email address.
            Enter it on the right to activate your account.
          </p>
          <div className="auth-chips">
            <span className="chip"><span className="chip-dot"/>One-time code</span>
            <span className="chip"><span className="chip-dot"/>Expires in 24h</span>
          </div>
        </div>
      </div>

      <div className="auth-right">
        <div className="auth-card anim">
          <h1 className="auth-title">Verify email</h1>
          {user && <p className="auth-sub">Logged in as <strong>{user.email}</strong></p>}

          {!sent ? (
            <>
              <p className="dim mt4">Click the button below and we'll send a 6-digit code to your inbox.</p>
              <button className="btn-main" onClick={sendOtp} disabled={sending}>
                {sending ? <span className="spin"/> : 'Send OTP →'}
              </button>
            </>
          ) : (
            <form onSubmit={verify}>
              <p className="dim" style={{marginBottom:'1rem'}}>Enter the 6-digit code sent to your email.</p>
              <div className="otp-row">
                {otp.map((d, i) => (
                  <input
                    key={i} ref={(el) => (refs.current[i] = el)}
                    className="otp-box" maxLength={1} value={d}
                    onChange={(e) => handleOtp(i, e.target.value)}
                    onKeyDown={(e) => handleKey(i, e)}
                    inputMode="numeric"
                  />
                ))}
              </div>
              <button className="btn-main" disabled={verifying}>
                {verifying ? <span className="spin"/> : 'Verify Email'}
              </button>
              <button type="button" className="btn-sec" onClick={sendOtp} disabled={sending}>
                {sending ? 'Resending…' : 'Resend OTP'}
              </button>
            </form>
          )}
        </div>
      </div>
    </div>
  );
}
