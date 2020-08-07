package quiz.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Quiz1 {
	class ProductInfo {
		final String code;
		final String name;

		public ProductInfo(String code, String name) {
			this.code = code;
			this.name = name;
		}

		@Override
		public String toString() {
			return "ProductInfo [code=" + code + ", name=" + name + "]";
		}
	}

	class ProductStock {
		final String productCode;
		final int stock;

		public ProductStock(String productCode, int stock) {
			this.productCode = productCode;
			this.stock = stock;
		}

		@Override
		public String toString() {
			return "ProductStock [productCode=" + productCode + ", stock=" + stock + "]";
		}
	}

	class ProductImage {
		final String productCode;
		final String url;

		public ProductImage(String productCode, String url) {
			this.productCode = productCode;
			this.url = url;
		}

		@Override
		public String toString() {
			return "ProductImage [productCode=" + productCode + ", url=" + url + "]";
		}
	}

	class Product {
		final String code;
		final String name;
		final ProductStock stock;
		final ProductImage[] images;

		public Product(String code, String name, ProductStock stock, ProductImage[] images) {
			this.code = code;
			this.name = name;
			this.stock = stock;
			this.images = images;
		}

		@Override
		public String toString() {
			return "Product [code=" + code + ", name=" + name + ", stock=" + stock + ", images="
					+ Arrays.toString(images) + "]";
		}
	}

	List<ProductInfo> getProductInfoList() {
		List<ProductInfo> infos = new ArrayList<>();
		infos.add(new ProductInfo("code1", "name1"));
		infos.add(new ProductInfo("code2", "name2"));
		infos.add(new ProductInfo("code3", "name3"));
		return infos;
	}

	List<ProductStock> getProductStockList() {
		List<ProductStock> stocks = new ArrayList<>();
		stocks.add(new ProductStock("code3", 3));
		stocks.add(new ProductStock("code1", 1));
		stocks.add(new ProductStock("code2", 2));
		return stocks;
	}

	List<ProductImage> getProductImageList() {
		List<ProductImage> images = new ArrayList<>();
		images.add(new ProductImage("code1", "code1url2"));
		images.add(new ProductImage("code2", "code2url1"));
		images.add(new ProductImage("code1", "code1url1"));
		images.add(new ProductImage("code3", "code3url1"));
		images.add(new ProductImage("code2", "code2url2"));
		images.add(new ProductImage("code3", "code3url2"));
		return images;
	}

	List<Product> getProductList() {
		List<ProductInfo> infos = getProductInfoList();
		List<ProductStock> stocks = getProductStockList();
		List<ProductImage> images = getProductImageList();

		List<Product> products = new ArrayList<>();
		for (int i = 0; i < infos.size(); i++) {
			String productCode = infos.get(i).code;
			String productName = infos.get(i).name;
			ProductStock productStock = new ProductStock("", 0);
			ArrayList<ProductImage> productImages = new ArrayList<>();

			for (int s = 0; s < stocks.size(); s++) {
				ProductStock stock = stocks.get(s);
				if (stock.productCode.equals(productCode)) {
					productStock = stock;
				}
			}

			for (int m = 0; m < images.size(); m++) {
				ProductImage image = images.get(m);
				if (image.productCode.equals(productCode)) {
					productImages.add(image);
				}
			}

			ProductImage[] convertedProductImages = new ProductImage[productImages.size()];
			for(int c = 0; c < productImages.size(); c++){
				convertedProductImages[c] = productImages.get(c);
			}

			products.add(new Product(productCode, productName, productStock, convertedProductImages));
		}

		return products;
	}

	public void validate(List<Product> products) throws Exception {
		System.out.println("Validate Result:");
		System.out.println(products);
		if (products == null || products.isEmpty()) {
			throw new Exception("Err-00");
		}
		if (products.size() != 3) {
			throw new Exception("Err-01");
		}
		for (Product product : products) {
			switch (product.code) {
				case "code1":
					validateItem(product, 1);
					break;
				case "code2":
					validateItem(product, 2);
					break;
				case "code3":
					validateItem(product, 3);
					break;
				default:
					throw new Exception("Err-02");
			}
		}
	}

	public void validateItem(Product product, int index) throws Exception {
		if (!product.name.equals("name" + index)) {
			throw new Exception("Err-03");
		}
		if (product.stock == null) {
			throw new Exception("Err-04");
		}
		if (!product.stock.productCode.equals("code" + index)) {
			throw new Exception("Err-05");
		}
		if (product.stock.stock != index) {
			throw new Exception("Err-06");
		}
		if (product.images == null) {
			throw new Exception("Err-07");
		}
		if (product.images.length != 2) {
			throw new Exception("Err-08");
		}
		Set<String> imageUrlSet = new HashSet<>();
		for (ProductImage image : product.images) {
			if (!image.productCode.equals("code" + index)) {
				throw new Exception("Err-09");
			}
			if (!image.url.startsWith("code" + index + "url")) {
				throw new Exception("Err-10");
			}
			if (imageUrlSet.contains(image.url)) {
				throw new Exception("Err-11");
			}
			imageUrlSet.add(image.url);
		}
	}

	public static void main(String[] args) throws Exception {
		Quiz1 quiz = new Quiz1();
		List<Product> products = quiz.getProductList();
		quiz.validate(products);
	}
}
