import React, { useEffect, useState } from 'react'
import AddProductsForm from '../AdminComponents/AddProductsForm';
import AddProductVariantForm from '../AdminComponents/AddProductVariantForm';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import NavigationBar from '../../src/Components/NavigationBar'
import axios from 'axios';
import EditProductForm from '../AdminComponents/EditProductForm';
import { getCookie } from '../Handlers/RouteHandlers';
const SellerHomePage = () => {
  const [editViewActive, setEditViewActive] = useState(false);
  const [addProductViewActive, setAddProductViewActive] = useState(false);
  const [addProducVariantViewActive, setAddProductVariantViewActive] = useState(false);

  const [activeSelection, setActiveSelection] = useState({});

  const [sellerProductVariants,setSellerProductVariants] = useState([]);
  console.log(sellerProductVariants)

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get("http://localhost:8080/seller/product/seller/a1885a79-f098-4d78-bb10-8e72ff2fab91", {
          headers: {
            Authorization: `Bearer ${getCookie("token")}`          
          },
        });
        setSellerProductVariants(response.data.products);
      } catch (error) {
        console.log(error);
      }
    };
  
    fetchData();
  }, []); // Empty dependency array to run once on mount #382dd2
//  console.log(sellerProductVariants[0].productImages[0])

  return (
    <>{setSellerProductVariants && <div className='relative h-screen'>
      {/* Products Table with following functions
      ->editProduct 
      ->delete 
      ->addProduct 
      ->addVariant */}
      <div className=" flex flex-col w-[80%] mx-auto gap-4">

        <div className="">
            <button onClick={()=>setAddProductViewActive(true)} className=" text-pink-600 p-2 font-medium border border-pink-600 rounded-sm hover:bg-pink-600 hover:text-white" >Add New Product</button>
        </div>
      {/* Table */}
      <div className="w-[100%]">
        <table className='w-full border-collapse'>
          <thead>
            <tr className="text-white bg-pink-600">
              <th className="w-[20%] py-2">Img</th>
              <th className="w-[30%] py-2">Title</th>
              <th className="w-[20%] py-2">Price</th>
              <th className="w-[20%] py-2">Stock</th>
              <th className="w-[10%] py-2">Options</th>
            </tr>
          </thead>
          <tbody>
          {   
              sellerProductVariants.map((item,index)=>(
                <tr className="hover:bg-pink-50 transition ease-in border-b-2">
              <td className="text-center">
                <div className="bg-gray-200 h-16 w-16 mx-auto">
                  <img src={item.productImages.length>0? item.productImages[0].imageUrl:"/woman.png"} alt="" className=" object-contain w-full h-full" />
                </div>
              </td>
              <td className=" text-center">{item.productTitle}</td>
              <td className=" text-center">Rs {item.price}</td>
              <td className=" text-center">{item.stockCount}</td>
              <td className=" flex justify-center space-x-2">
                <button className="text-blue-500 rounded-md m-2 px-2 py-1 border border-blue-600 hover:bg-blue-600 hover:text-white" onClick={() => {setAddProductVariantViewActive(true);setActiveSelection(item)}}>Add Variant</button>
                <button className="text-yellow-600 rounded-md m-2 px-2 py-1 border border-yellow-600 hover:bg-yellow-600 hover:text-white" onClick={() => {setEditViewActive(true) ; setActiveSelection(item)}}><EditIcon/></button>
                <button className="text-red-600 rounded-md m-2 px-2 py-1 border border-red-600 hover:bg-red-600 hover:text-white"><DeleteIcon/></button>
              </td>
            </tr>
              ))
            }
          </tbody>
        </table>
      </div>
      
      {/*Add Products Form */}
      {addProducVariantViewActive && (
        <div className="absolute top-0 left-0 right-0 bottom-0  p-4 shadow-lg  bg-black bg-opacity-50 backdrop-blur-sm">
          <AddProductVariantForm prop= {activeSelection}  setAddProductVariantViewActive={setAddProductVariantViewActive} />
        </div>
      )}

      {/*Add variant Form */}
      {addProductViewActive && (
        <div className="absolute top-0 left-0 right-0 bottom-0  p-4 shadow-lg  bg-black bg-opacity-50 backdrop-blur-sm">
          <AddProductsForm setAddProductViewActive={setAddProductViewActive} />
        </div>
      )}

      {/* Edit/Add Products Form */}
      {editViewActive && (
        <div className="absolute top-0 left-0 right-0 bottom-0  p-4 shadow-lg  bg-black bg-opacity-50 backdrop-blur-sm">
          <EditProductForm prop= {activeSelection} setEditViewActive={setEditViewActive} />
        </div>
      )}
      </div>




    </div>}</>
  )
}

export default SellerHomePage;
