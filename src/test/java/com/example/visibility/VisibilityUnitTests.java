package com.example.visibility;

import com.example.visibility.model.Product;
import com.example.visibility.model.Size;
import com.example.visibility.repository.ProductRepository;
import com.example.visibility.repository.SizeRepository;
import com.example.visibility.repository.StockRepository;
import com.example.visibility.service.VisibilityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class VisibilityUnitTests {

	@InjectMocks
	private VisibilityService visibilityService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private SizeRepository sizeRepository;

	@Mock
	private StockRepository stockRepository;

	private final int SAMPLE_PRODUCT_ID = 1;
	private final int SAMPLE_SIZE_NORMAL_ID = 11;
	private final int SAMPLE_SIZE_SPECIAL_ID = 12;

	@Test
	void testEmptyProductList() {
		when(productRepository.findAll()).thenReturn(new TreeSet<>());

		assertThat(visibilityService.getVisibleProducts(), empty());
	}

	@Test
	void testProductHasNormalSizes() {
		when(productRepository.findAll()).thenReturn(productListSample());

		when(sizeRepository.findSizesByProductId(SAMPLE_PRODUCT_ID)).thenReturn(sizeNormalSampleList(false));

		when(stockRepository.findStockBySizeId(SAMPLE_SIZE_NORMAL_ID)).thenReturn(2);

		assertThat(visibilityService.getVisibleProducts(), contains(SAMPLE_PRODUCT_ID));
	}

	@Test
	void testProductHasNormalSizesWithoutStock() {
		when(productRepository.findAll()).thenReturn(productListSample());

		when(sizeRepository.findSizesByProductId(SAMPLE_PRODUCT_ID)).thenReturn(sizeNormalSampleList(false));

		when(stockRepository.findStockBySizeId(SAMPLE_SIZE_NORMAL_ID)).thenReturn(0);

		assertThat(visibilityService.getVisibleProducts(), empty());
	}

	@Test
	void testProductNormalSizesBackSoon() {
		when(productRepository.findAll()).thenReturn(productListSample());

		when(sizeRepository.findSizesByProductId(SAMPLE_PRODUCT_ID)).thenReturn(sizeNormalSampleList(true));

		//This code won't be call
		//when(stockRepository.findStockBySizeId(SAMPLE_SIZE_NORMAL_ID)).thenReturn(0);

		assertThat(visibilityService.getVisibleProducts(), contains(SAMPLE_PRODUCT_ID));
	}

	@Test
	void testProductHasSpecialSizes() {
		when(productRepository.findAll()).thenReturn(productListSample());

		when(sizeRepository.findSizesByProductId(SAMPLE_PRODUCT_ID)).thenReturn(sizeSpecialSampleList(false));

		when(stockRepository.findStockBySizeId(SAMPLE_SIZE_SPECIAL_ID)).thenReturn(2);

		assertThat(visibilityService.getVisibleProducts(), empty());
	}

	@Test
	void testProductHasSpecialSizesWithoutStock() {
		when(productRepository.findAll()).thenReturn(productListSample());

		when(sizeRepository.findSizesByProductId(SAMPLE_PRODUCT_ID)).thenReturn(sizeSpecialSampleList(false));

		when(stockRepository.findStockBySizeId(SAMPLE_SIZE_SPECIAL_ID)).thenReturn(0);

		assertThat(visibilityService.getVisibleProducts(), empty());
	}

	@Test
	void testProductSpecialSizesBackSoon() {
		when(productRepository.findAll()).thenReturn(productListSample());

		when(sizeRepository.findSizesByProductId(SAMPLE_PRODUCT_ID)).thenReturn(sizeSpecialSampleList(true));

		//This code won't be call
		//when(stockRepository.findStockBySizeId(SAMPLE_SIZE_SPECIAL_ID)).thenReturn(0);

		assertThat(visibilityService.getVisibleProducts(), empty());
	}

	@Test
	void testProductHasSpecialAndNormalSizesWithStock() {
		when(productRepository.findAll()).thenReturn(productListSample());

		when(sizeRepository.findSizesByProductId(SAMPLE_PRODUCT_ID)).thenReturn(Stream
				.concat(sizeSpecialSampleList(false).stream(),
						sizeNormalSampleList(false).stream())
				.collect(Collectors.toList()));

		when(stockRepository.findStockBySizeId(SAMPLE_SIZE_NORMAL_ID)).thenReturn(2);
		when(stockRepository.findStockBySizeId(SAMPLE_SIZE_SPECIAL_ID)).thenReturn(3);

		assertThat(visibilityService.getVisibleProducts(), contains(1));
	}

	@Test
	void testProductHasSpecialAndNormalSizesBackSoon() {
		when(productRepository.findAll()).thenReturn(productListSample());

		when(sizeRepository.findSizesByProductId(SAMPLE_PRODUCT_ID)).thenReturn(Stream
				.concat(sizeSpecialSampleList(true).stream(),
						sizeNormalSampleList(true).stream())
				.collect(Collectors.toList()));

		//This code won't be call
		//when(stockRepository.findStockBySizeId(SAMPLE_SIZE_NORMAL_ID)).thenReturn(0);
		//when(stockRepository.findStockBySizeId(SAMPLE_SIZE_SPECIAL_ID)).thenReturn(0);

		assertThat(visibilityService.getVisibleProducts(), contains(1));
	}

	private Set<Product> productListSample(){
		Set<Product> products = new TreeSet<>();

		products.add(Product.builder()
						.id(SAMPLE_PRODUCT_ID)
						.sequence(10)
						.build());

		return products;
	}

	private List<Size> sizeNormalSampleList(boolean isBackSoon){
		List<Size> sizes = new ArrayList<>();

		sizes.add(Size.builder()
					.id(SAMPLE_SIZE_NORMAL_ID)
					.special(false)
					.backSoon(isBackSoon)
					.productId(SAMPLE_PRODUCT_ID)
					.build());
		return sizes;
	}

	private List<Size> sizeSpecialSampleList(boolean isBackSoon){
		List<Size> sizes = new ArrayList<>();

		sizes.add(Size.builder()
				.id(SAMPLE_SIZE_SPECIAL_ID)
				.special(true)
				.backSoon(isBackSoon)
				.productId(SAMPLE_PRODUCT_ID)
				.build());
		return sizes;
	}
}
