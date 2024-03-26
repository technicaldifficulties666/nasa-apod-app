import Home from '../components/Home';
import Apod from '../components/Apod';
import Signin from '../components/Signin';
import Signup from '../components/Signup';

const routes = [
  { path: '/home', component: <Home />, exact: true },
  {path: '/', component: <Home /> },
  { path: '/apod', component: <Apod /> },
  {path: '/signin', component: <Signin /> },
  {path: '/signup', component: <Signup /> },
];

export default routes;