export default function AuthPanel({ headline, sub, badges = [] }) {
  return (
    <div className="auth-panel">
      <div className="auth-panel-bg" />
      <div className="auth-panel-grid" />
      <div className="auth-panel-content">
        <div className="auth-panel-logo">
          Access<span>Point</span>
        </div>
        <h2 className="auth-panel-headline">{headline}</h2>
        <p className="auth-panel-sub">{sub}</p>
        {badges.length > 0 && (
          <div className="auth-panel-badges">
            {badges.map((b, i) => (
              <span key={i} className="badge-pill">
                <span className="dot" />
                {b}
              </span>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
