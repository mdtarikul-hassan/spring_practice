import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function ProfilePage() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  if (!user) {
    navigate('/login');
    return null;
  }

  const initial = user.name ? user.name[0].toUpperCase() : '?';

  return (
    <div className="profile-page">
      <div className="profile-card anim">
        <div className="avatar">{initial}</div>
        <h2 style={{fontFamily:'var(--ff-head)', fontWeight:700, fontSize:'1.5rem', letterSpacing:'-.03em'}}>{user.name}</h2>
        <p style={{color:'var(--text-dim)', fontSize:'.9rem'}}>{user.email}</p>
        {user.accountVerified
          ? <span className="verified-tag">✓ Email Verified</span>
          : <span className="unverified-tag">⚠ Email Not Verified</span>
        }

        <div className="info-row">
          <div className="info-label">User ID</div>
          <div className="info-value" style={{fontFamily:'monospace', fontSize:'.85rem', color:'var(--text-dim)'}}>{user.userId}</div>
        </div>

        <div style={{marginTop:'2rem', display:'flex', flexDirection:'column', gap:'10px'}}>
          {!user.accountVerified && (
            <Link to="/verify-email" className="btn-main" style={{textDecoration:'none', justifyContent:'center'}}>
              Verify Email
            </Link>
          )}
          <button className="btn-sec" onClick={logout}>Sign Out</button>
        </div>
      </div>
    </div>
  );
}
