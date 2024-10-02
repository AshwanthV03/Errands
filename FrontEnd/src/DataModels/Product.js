export class Product {
    constructor({ productId, title, description, productVariant }) {
      this.productId = productId;  // The server response doesn't have a `productId`, you may need to handle this differently.
      this.title = title;
      this.description = description;
      // Assume `productVariant` is an array, map each variant to a `ProductVariant` instance
      this.productVariant = productVariant.map((variant) => new ProductVariant(variant));
    }
  }

  export class ProductVariant {
    constructor({ variantId, size, price, stockCount, discount, images, timeStamp }) {
      this.variantId = variantId;
      this.size = size;
      this.price = price;
      this.stockCount = stockCount;  // Correct field name
      this.discount = discount;
      // The images are already in the correct format, so no need to wrap each in an object
      this.images = images;  
      this.timeStamp = new Date(timeStamp);  // Convert timeStamp to a JavaScript Date object
    }
  }
