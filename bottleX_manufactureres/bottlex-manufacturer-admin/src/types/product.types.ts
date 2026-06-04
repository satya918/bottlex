export interface Category {
    id: string;
    categoryName: string;
}

export interface Product {
    id: string;

    productName: string;
    productCode: string;
    sku: string;

    description: string;

    category: Category;

    price: number;

    stockQuantity: number;

    active: boolean;

    manufacturer: string;

    createdAt: string;

    updatedAt: string;

    images?: string[];
}