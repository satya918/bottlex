import { useEffect, useState } from "react";

import { ProductsAPI } from "../api/products.api";

export const useProducts = (
    page: number,
    search: string
) => {

    const [products, setProducts] =
        useState<any[]>([]);

    const [totalPages, setTotalPages] =
        useState(0);

    const [loading, setLoading] =
        useState(true);

    const fetchProducts = async () => {

        try {

            setLoading(true);

            const res =
                await ProductsAPI.getProducts(
                    page,
                    10,
                    search
                );

            setProducts(res.data.content);

            setTotalPages(res.data.totalPages);

        } catch (err) {

            console.log(err);

        } finally {

            setLoading(false);
        }
    };

    useEffect(() => {

        fetchProducts();

    }, [page, search]);

    return {
        products,
        totalPages,
        loading,
        refetch: fetchProducts,
    };
};