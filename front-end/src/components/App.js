import "../styles/App.css";
import NavBar from "./Navbar";
import routes from "../utils/routes";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
  return (
    
      <Router>
        <NavBar />
        <div className="container mt-4">
          <Routes>
            {routes.map((route, index) => (
              <Route key={index} path={route.path} element={route.component} />
            ))}
          </Routes>
        </div>
      </Router>
   
  );
}

export default App;
