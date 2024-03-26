import React from "react";
import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import logo from "../images/logo.svg";
import {Link} from "react-router-dom";
import "../styles/App.css";
import {connect} from "react-redux";
import {logout} from "../utils/authActions";
import useHandleSignOut from "../utils/useHandleSignOut";

const NavBar = ({isLoggedIn, username, logout}) => {
  const handleSignOut = useHandleSignOut();
  //const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);
  return (
    <Navbar /*bg="myColor"*/ className="navbar navbar-dark bg-dark py-0">
      <Container>
        <Navbar.Brand as={Link} to="/home">
          <img
            alt=""
            src={logo}
            width="70"
            height="70"
            className="d-inline-block align-top App-logo"
          />{" "}
        </Navbar.Brand>
        <Navbar.Toggle />
        <Navbar.Collapse>
          <Nav className="me-auto">
            {isLoggedIn ? (
              <Nav.Link as={Link} to="/apod">
                APOD
              </Nav.Link>
            ) : (
              <Nav.Link disabled={true}>APOD</Nav.Link>
            )}
          </Nav>
          {isLoggedIn ? (
            <>
              <Nav className="ms-auto">
                <Navbar.Text style={{color: "white"}}>
                  Welcome {username}!
                </Navbar.Text>
                <Nav.Link onClick={handleSignOut}>Sign Out</Nav.Link>
              </Nav>
            </>
          ) : (
            <Nav className="ms-auto">
              <Nav.Link as={Link} to="/signin">
                Sign in
              </Nav.Link>
              <Nav.Link as={Link} to="/signup">
                Create an account
              </Nav.Link>
            </Nav>
          )}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

const mapStateToProps = (state) => ({
  isLoggedIn: state.auth.isLoggedIn,
  username: state.auth.username,
  password: state.auth.password,
});
// const mapStateToProps = (state) => ({
//   isLoggedIn: state.auth ? state.auth.isLoggedIn : false
// });

//export default connect(mapStateToProps)(NavBar);
export default connect(mapStateToProps, {logout})(NavBar);
//export default NavBar;
