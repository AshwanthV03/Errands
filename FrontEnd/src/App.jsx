import { Route, Router,Routes,S } from 'react-router-dom';
import NavigationBar from './Components/NavigationBar';
import HomePage from './Pages/HomePage';
import ProductPage from './Pages/ProductPage';
import ProductsPage from './Pages/ProductsPage';
import RegisterPage from './Pages/RegisterPage';
import Login from './Pages/Login';
import { useDispatch, useSelector } from 'react-redux';
import AddProductPage from './SellerPages/AddProductPage';
import SellerHomePage from './SellerPages/SellerHomePage';
import CartPage from './Pages/CartPage';
import CheckoutPage from './Pages/CheckoutPage';
import Profile from './Pages/Profile';
import { getCookie } from './Handlers/RouteHandlers';

function App() {
  const user = useSelector((state) => state.user.User);
  const cookie = getCookie("token")
  return (
    <div classname="">
        <Routes>
                {/* <Route path="/" element={user!=null?<HomePage />:<Login/>} /> */}
                <Route path="/" element={user&&cookie?<HomePage />:<Login/>} />
                <Route path="/products" element={user&&cookie ? <ProductsPage />:<Login/>} />
                <Route path="/product/:id" element={user&&cookie?<ProductPage />:<Login/>} />
                <Route path="/Register" element={<RegisterPage/>} />
                <Route path='/login' element={<Login/>}/>
                <Route path='/cart' element={user&&cookie?<CartPage/>:<Login/>}/>
                <Route path='/checkout/:id' element={user&&cookie?<CheckoutPage/>:<Login/>}/>
                <Route path='/profile' element={user&&cookie?<Profile/>:<Login/>}/>
                
                <Route path='/seller/addProducts' element={<AddProductPage/>}/>
                <Route path='/seller/' element={<SellerHomePage/>}/>                
            </Routes>
    </div>
  );
}

export default App;
