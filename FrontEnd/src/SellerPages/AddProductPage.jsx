import React from 'react'
import AddProductsForm from '../AdminComponents/AddProductsForm'

const AddProductPage = () => {
  return (
    <div>
      <div className="flex flex-row">
        <div className="w-1/2">Add Product</div>
        <div className="w-1/2"><AddProductsForm/></div>
         
      </div>
    </div>
  )
}

export default AddProductPage