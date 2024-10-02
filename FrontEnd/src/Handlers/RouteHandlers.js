export function getCookie(name) {
    const value = `; ${document.cookie}`; // Add a leading semicolon to handle the edge case
    const parts = value.split(`; ${name}=`); // Split cookie string by the cookie name
    if (parts.length === 2) {
        return parts.pop().split(';').shift(); // Get the value before the next semicolon or end of string
    }
    return null; // Return null if the cookie is not found
}