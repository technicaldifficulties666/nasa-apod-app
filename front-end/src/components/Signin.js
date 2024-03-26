import React from "react";
import {Formik, Form, Field, ErrorMessage} from "formik";
import axios from "axios";
import {useNavigate} from "react-router-dom";
//import { useDispatch } from 'react-redux';
import { login } from '../utils/authActions';
import { connect } from 'react-redux';
import "../styles/App.css";

const Signin = ({ login }) => {
  //const dispatch = useDispatch();
  const initialValues = {username: "", password: ""};
  const redirect = useNavigate(); // Initialize useHistory hook

  const handleSubmit = async (values, {setSubmitting}) => {
    try {
      // Encode username and password to Base64
      const credentials = btoa(`${values.username}:${values.password}`);
      // Make API request to login endpoint
      const response = await axios.post(
        "http://localhost:8080/api/auth/signin",
        {
          username: values.username,
          password: values.password,
        },
        {
          headers: {
            Authorization: `Basic ${credentials}`,
          },
        }
      ); //values);
      console.log("Login successful: ", response.data);
      // Redirect user to home page
      //dispatch(login());
      login(values.username, values.password);
      redirect("/home");
    } catch (error) {
      console.error("Login failed: ", error);
    }
    setSubmitting(false);
  };

  return (
    <section className="vh-50">
      <div className="container py-5 h-50">
        <div className="row d-flex justify-content-center align-items-center h-50">
          <div className="col-12 col-md-8 col-lg-6 col-xl-5">
            <div
              className="card bg-dark text-white"
              style={{borderRadius: "1rem"}}
            >
              <div className="card-body p-5 text-center">
                <div className="mb-md-2 mt-md-2 pb-1">
                  <h2 className="fw mb-3">Sign In</h2>
                  <p className="text-white-50 mb-4">
                    Please enter your login and password!
                  </p>
                  <Formik initialValues={initialValues} onSubmit={handleSubmit}>
                    {({isSubmitting}) => (
                      <Form>
                        <div className="form-outline form-white mb-4">
                          <Field
                            type="username"
                            placeholder="Username"
                            name="username"
                            data-bs-theme="dark"
                            className="form-control form-control-lg"
                          />
                          <ErrorMessage name="username" component="div" />
                        </div>
                        <div className="form-outline form-white mb-4">
                          <Field
                            type="password"
                            placeholder="Password"
                            name="password"
                            data-bs-theme="dark"
                            className="form-control form-control-lg"
                          />
                          <ErrorMessage name="password" component="div" />
                        </div>
                        <p className="small mb-3 pb-lg-2">
                          <a className="text-white-50" href="#!">
                            Forgot password?
                          </a>
                        </p>
                        <button
                          className="btn btn-outline-light btn-lg px-5 mb-4"
                          type="submit"
                          disabled={isSubmitting}
                        >
                          Submit
                        </button>
                      </Form>
                    )}
                  </Formik>

                  <div>
                    <p className="mb-0">
                      Don't have an account?{" "}
                      <a href="/signup" className="text-white-50 fw-bold">
                        Sign Up
                      </a>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

// const mapDispatchToProps = (dispatch) => ({
//   login: () => dispatch({ type: "LOGIN" }),
//   //logou: () => dispatch({ type: "DECREMENT" })
// });
//export default Signin;
export default connect(null, { login })(Signin);
//export default connect(mapStateToProps, { login })(Signin);
//export default connect(mapStateToProps, mapDispatchToProps)(Signin);