import React from "react";
import axios from "axios";
import {useState, useEffect} from "react";
import {useSelector} from "react-redux";
//import {Calendar} from "primereact/calendar";



function Apod() {
  const {username, password} = useSelector((state) => state.auth);
  const [apodData, setApodData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchAPOD = async () => {
      const credentials = btoa(`${username}:${password}`);
      //let date = new Date();
      // let goBack = 0;
      // const dateString = `${today.getFullYear()}-${today.getMonth() + 1}-${
      //   today.getDate() - goBack
      // }`;
      try {
        const response = await axios.get(
          "http://localhost:8080/api/apod", //&date=${dateString},
          {
            headers: {
              Authorization: `Basic ${credentials}`,
            },
          }
          /*`${NASA_APOD_URL}?api_key=${API_KEY}`*/
        );
        console.log("apod response: ", response);
        if (!response.data) {
          throw new Error("Failed to fetch data");
        }
        const data = await response.data;
        setApodData(data);
        console.log("APOD data: ", data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchAPOD();
  }, [username, password]);

  //if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  //if (!apodData) return null;
  return (
    // <div>
    //   <p className="lead">
    //     Each day a different image or photograph of our fascinating universe is
    //     featured, along with a brief explanation written by a professional
    //     astronomer.
    //   </p>
    //   <div>
    //     <h3 className="display-6">
    //       Astronomy picture of the day: {apodData.title}
    //     </h3>
    //     <img src={apodData.url} alt={apodData.title} />
    //     <p>{apodData.explanation}</p>
    //   </div>
    // </div>

    <div
      style={{backgroundColor: "#e5e5e5", borderRadius: "0.5rem"}}
      className="container-fluid"
    >
      <div className="row" style={{padding: "4rem 2rem 4rem 2rem"}}>
        <div className="col-md-10 offset-md-1 col-xs-12">
          <div className="jumbotron">
            <h1 className="display-3" style={{fontSize: "3rem"}}>
              Astronomy Photo Of the Day
            </h1>

            {loading && (
              <div>
                <br />
                <br />
                <br />
                <span className="spinner-grow text-muted"></span>
                <span className="spinner-grow text-success"></span>
                <span className="spinner-grow text-info"></span>
                <span className="spinner-grow text-warning"></span>
                <span className="spinner-grow text-danger"></span>
                <span className="spinner-grow text-secondary"></span>
                <span className="spinner-grow text-dark"></span>
              </div>
            )}
            {apodData && (
              <div>
                <p className="text-danger">{error}</p>
                <label>Date:</label>&nbsp;&nbsp;
                {/* <span className="text-success">Try changing it!</span> */}
                <br />
                <br />
                {/* <ImaggaColor imgURL={data.url}/> */}
                {/* <MyColorify imgURL = {data.url}/> */}
                {/* <img imgURL = {apodData.url}/> */}
                <br />
                <div className="row">
                  <div className="col-md-6">
                    <a
                      href={apodData.url}
                      target="_blank"
                      rel="noopener noreferrer"
                    >
                      <img
                        alt={apodData.explanation}
                        className="img-fluid rounded"
                        src={apodData.url}
                      />
                    </a>
                  </div>
                  <div className="col-md-6">
                    <h2 className="lead" style={{fontSize: "2rem"}}>
                      {apodData.title}
                    </h2>
                    <p>{apodData.copyright}</p>
                    <p>
                      <a
                        target="_blank"
                        rel="noopener noreferrer"
                        href={apodData.hdurl}
                      >
                        View HD
                      </a>
                    </p>
                    <p>{apodData.explanation}</p>
                    <p>Media type: {apodData.media_type}</p>
                    <p>Service version: {apodData.service_version}</p>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

// const mapStateToProps = (state) => ({
//   username: state.auth.username,
//   password: state.auth.password,
// });

//export default connect(mapStateToProps, {logout})(Apod);

export default Apod;
