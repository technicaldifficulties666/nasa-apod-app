import {useDispatch} from "react-redux";
import {logout} from "./authActions";
import {useNavigate} from "react-router-dom";

const useHandleSignOut = () => {
  const dispatch = useDispatch();
  const redirect = useNavigate();

  const handleSignOut = () => {
    // Dispatch the logout action
    dispatch(logout());
    console.log("signed out!");
    // Redirect to the desired location
    redirect("/home");
  };

  return handleSignOut;
};

export default useHandleSignOut;
