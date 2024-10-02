import {createSlice} from '@reduxjs/toolkit'

const initialState ={
    User:null
}

const userSlice = createSlice({
    name:"user",
    initialState,
    reducers:{
            login:(state,action)=>{
                const user = {
                    userId : action.payload.userId,
                    userName : action.payload.userName
                }
                state.User = user;               
            },

            logout:(state)=>{
                state.User = null
            }
    }
})
export const {login,logout} =userSlice.actions;
export default userSlice.reducer;