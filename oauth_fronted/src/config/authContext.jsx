import { createContext, useContext, useEffect, useState } from "react";
import Keycloak from "keycloak-js";

// 1.
// eslint-disable-next-line react-refresh/only-export-components
export const AuthContext = createContext();

//2.
export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [keyCloakInstance, setKeyCloakInstance] = useState(null);

  useEffect(() => {
    //app start:

    Keycloak
      .init({
        onLoad: "check-sso",
        pkceMethod: "S256",
      })
      .then((authenticated) => {
        console.log("user logged in ");
        setIsAuthenticated(authenticated);
        setKeyCloakInstance(Keycloak);
      })
      .catch((error) => {
        console.log("user is not logged in");
        console.log(error);
      });
  }, []);

  return (
    <AuthContext.Provider
      value={{
        isAuthenticated,
        setIsAuthenticated,
        keyCloakInstance,
        setKeyCloakInstance,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

//3.

// eslint-disable-next-line react-refresh/only-export-components
export const useAuth = () => useContext(AuthContext);