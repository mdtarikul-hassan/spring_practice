import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const features = [
  { icon: '🔐', title: 'JWT Authentication',     desc: 'Stateless, secure tokens stored in HttpOnly cookies — protected against XSS attacks.' },
  { icon: '📧', title: 'Email Verification',     desc: 'OTP sent to your inbox confirms your identity and activates your account.' },
  { icon: '🔄', title: 'Password Reset',         desc: 'Forgot your password? Request a reset OTP anytime — no account lockout.' },
  { icon: '🛡️', title: 'Spring Security',       desc: 'Enterprise-grade backend security powered by Spring Security and BCrypt hashing.' },
  { icon: '⚡', title: 'Vite + React',           desc: 'Lightning-fast frontend with hot module replacement and zero-config setup.' },
  { icon: '🗄️', title: 'MySQL Persistence',     desc: 'Relational database stores users reliably with JPA auto-migration on startup.' },
];

export default function HomePage() {
  const { user } = useAuth();

  return (
    <>
      {/* ── HERO ── */}
      <section className="hero">
        <div className="hero-bg" />
        <div className="hero-grid" />
        <div className="hero-inner">
          <div className="hero-badge">
            <span className="hero-badge-dot" />
            Full-Stack Auth — Spring Boot + React
          </div>
          <h1 className="hero-h">
            Secure auth,<br />
            <span className="hero-h-grad">done right.</span>
          </h1>
          <p className="hero-p">
            A production-ready authentication system with JWT, email OTP verification,
            and password reset — built with Spring Boot and React Vite.
          </p>
          <div className="hero-btns">
            {user ? (
              <Link to="/profile" className="btn-hero btn-hero-primary">Go to Profile →</Link>
            ) : (
              <>
                <Link to="/register" className="btn-hero btn-hero-primary">Create Account →</Link>
                <Link to="/login"    className="btn-hero btn-hero-ghost">Sign In</Link>
              </>
            )}
          </div>
        </div>
      </section>

      {/* ── FEATURES ── */}
      <div className="features-wrap">
        <div className="sec-label">What's inside</div>
        <h2 className="sec-title">Everything you need</h2>
        <p className="sec-sub">
          A complete monolithic auth stack — register, verify, login, reset password.
          No microservices overhead, just clean, solid code.
        </p>
        <div className="feat-grid">
          {features.map((f) => (
            <div className="feat-card" key={f.title}>
              <div className="feat-icon">{f.icon}</div>
              <div className="feat-title">{f.title}</div>
              <div className="feat-desc">{f.desc}</div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}
