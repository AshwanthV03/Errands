import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  CartItems: [],
  Count:0
};

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    loadCart: (state, action) => {
        console.log("from slice",action)
      state.CartItems = action.payload;
      state.Count = state.CartItems.length
    },

    removeItem: (state, action) => {
      state.CartItems = state.CartItems.filter(
        (ci) => ci.cartItem !== action.payload.id
        
      );
      state.Count = state.CartItems.length

    },

    addItem: (state) => {
      state.Count+=1
    },
  },
});

export const { loadCart, removeItem, addItem } = cartSlice.actions;
export default cartSlice.reducer;
