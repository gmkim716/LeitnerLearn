import React, { createContext, useState, useCallback } from "react";
import { getToken, removeToken, setToken } from "@/utils/tokenUtils";

interface UserInfo {
  token: string | null;
  user: {
    email: string | null;
  };
}

interface AuthContextType {
  isLoggedIn: boolean;
  userInfo: UserInfo;
  login: (loginData: UserInfo) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined
);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [userInfo, setUserInfo] = useState<UserInfo>({
    token: getToken(),
    user: {
      email: sessionStorage.getItem("email"),
    },
  });

  const login = useCallback((loginData: UserInfo): void => {
    setToken(loginData.token ?? "");
    sessionStorage.setItem("email", loginData.user.email ?? "");
    setUserInfo(loginData);
  }, []);

  const logout = useCallback((): void => {
    removeToken();
    sessionStorage.removeItem("email");
    setUserInfo({
      token: null,
      user: {
        email: null,
      },
    });
  }, []);

  const isLoggedIn = !!userInfo.token;
};
