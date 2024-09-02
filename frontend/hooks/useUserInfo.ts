import { parseJwt } from "@/pages/api/context/parseJwt";
import { useEffect, useState } from "react";

interface UserInfo {
  userId: number;
  email: string;
}

export default function useUserInfo() {
  const [userInfo, setUserInfo] = useState<UserInfo>();

  useEffect(() => {
    const token = sessionStorage.getItem("access-token");
    if (token) {
      const parsedUserInfo = parseJwt(token);
      setUserInfo(parsedUserInfo);
    }
  }, []);

  return userInfo;
}
