import React from "react";
import {useSelector} from "react-redux";
import {Link} from "react-router-dom";

function Home() {
  const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);
  return (
    <div
      style={{backgroundColor: "#e5e5e5", borderRadius: "0.5rem"}}
      className="container-fluid"
    >
      <div className="row" style={{padding: "4rem 2rem 4rem 2rem"}}>
        <div className="col-md-10 col-xs-12">
          <div className="jumbotron">
            <h1 className="display-3">Discover the cosmos!</h1>
            <p className="lead">
              Welcome to the NASA web portal. The objective of this site is to
              make NASA data, including imagery, accessible to astronomy
              enthusiasts. The media catalog is growing everyday.
            </p>
            {isLoggedIn ? (
              <>
                <p className="lead">
                  Start with{" "}
                  {/* <a href="/apod" style={{textDecoration: "none"}}>
                    Astronomy Picture of Day
                  </a>{" "} */}
                  <Link
                    style={{textDecoration: "none"}}
                    to={{pathname: "/apod", state: isLoggedIn}}
                  >
                    Astronomy Picture of Day
                  </Link>
                </p>{" "}
              </>
            ) : (
              <>
                <p className="lead">
                  <a href="/signup" style={{textDecoration: "none"}}>
                    Create an account
                  </a>{" "}
                  or{" "}
                  <a href="/signin" style={{textDecoration: "none"}}>
                    Sign in
                  </a>{" "}
                  and start with Astronomy Picture of Day
                </p>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;

