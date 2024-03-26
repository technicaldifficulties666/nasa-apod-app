import React from "react";
import {Formik, Form, Field, ErrorMessage} from "formik";
import * as Yup from 'yup';
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import "../styles/App.css";

const Signup = () => {
  const initialValues = {username: "", password: "", confirmPassword:"" };
  const redirect = useNavigate();

  const handleSubmit = async (values, {setSubmitting}) => {
    console.log("inside handle");
    try {
      // Make API request to login endpoint
      const response = await axios.post("http://localhost:8080/api/auth/signup", values);
      console.log("User registration successful: ", response.data);
      // Redirect user to home page or perform any other action
      redirect('/signin');
    } catch (error) {
      const errorData = JSON.parse(error.response.data);
      console.error("Parsed error data:", errorData);
       // alert(error.message);
      console.error("console.error: ", error.message);
      //const myObj = JSON.parse(error);
      console.error("Login failed:", error.AxiosError.message);
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
              <div className="card-body p[-5 text-center">
                <div className="mb-md-2 mt-md-2 pb-1">
                  <h2 className="fw mb-5">Create an account</h2>
                  <Formik initialValues={initialValues} 
                  validationSchema={Yup.object({
                    password: Yup.string()
                      .min(6, 'Password must be at least 6 characters')
                      .required('Required'),
                    confirmPassword: Yup.string()
                      .oneOf([Yup.ref('password'), null], 'Passwords must match')
                      .required('Required'),
                  })}
                  onSubmit={handleSubmit}>
                    {({isSubmitting}) => (
                      <Form>
                        <div className="form-outline form-white mb-4">
                          <Field
                            type="username"
                            placeholder="Choose a username"
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
                        <div className="form-outline form-white mb-5">
                          <Field
                            type="password"
                            placeholder="Confirm password"
                            name="confirmPassword"
                            data-bs-theme="dark"
                            className="form-control form-control-lg"
                          />
                          <ErrorMessage name="confirmPassword" component="div" />
                        </div>
                        <button
                          className="btn btn-outline-light btn-lg px-5 mb-4"
                          type="submit"
                          disabled={isSubmitting}
                        >
                          Register
                        </button>
                      </Form>
                    )}
                  </Formik>

                  <div>
                    <p className="mb-0">
                      Already have an account?{" "}
                      <a href="/signin" className="text-white-50 fw-bold">
                        Sign in here
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

export default Signup;
