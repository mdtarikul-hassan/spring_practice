import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { user, logout } = useAuth();
  return (
    <nav className="ap-nav">
      <Link to="/" className="ap-logo">Access<em>Point</em></Link>
      <div className="ap-nav-links">
        {user ? (
          <>
            <Link to="/profile" className="btn-nav-out">Profile</Link>
            <button onClick={logout} className="btn-nav-out">Sign Out</button>
          </>
        ) : (
          <>
            <Link to="/login"    className="btn-nav-out">Sign In</Link>
            <Link to="/register" className="btn-nav-in">Get Started</Link>
          </>
        )}
      </div>
    </nav>
  );
}
