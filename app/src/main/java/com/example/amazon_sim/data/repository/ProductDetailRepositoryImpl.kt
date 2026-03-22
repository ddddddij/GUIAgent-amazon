package com.example.amazon_sim.data.repository

import com.example.amazon_sim.domain.model.ProductDetailData
import com.example.amazon_sim.domain.model.ReviewTag
import com.example.amazon_sim.domain.model.SpecGroup
import com.example.amazon_sim.domain.model.SpecOption
import com.example.amazon_sim.domain.repository.ProductDetailRepository

class ProductDetailRepositoryImpl : ProductDetailRepository {

    private val productsMap: Map<String, ProductDetailData> by lazy {
        buildMap {
            val marshall = createMarshallActonIII()
            put("prod_003", marshall)
            put("marshall_acton3", marshall)

            put("prod_001", createIPhone17ProMax())
            put("prod_002", createMacBookAir())
            put("prod_004", createSonyWH1000XM5())
            put("prod_005", createNintendoSwitch())
            put("prod_006", createSamsungGalaxyWatch7())
            put("prod_007", createDysonV15())
            put("prod_008", createNespresso())
            put("prod_009", createKitchenAid())
            put("prod_010", createVitamix())
            put("prod_011", createNikePegasus())
            put("prod_012", createAdidasSoccerBall())
            put("prod_013", createWilsonBasketball())
            put("prod_014", createStarbucksCoffee())
            put("prod_015", createCocaCola())
            put("prod_016", createOreo())
        }
    }

    override fun getProductById(productId: String): ProductDetailData? {
        return productsMap[productId]
    }

    private fun createMarshallActonIII(): ProductDetailData {
        return ProductDetailData(
            id = "prod_003",
            name = "Marshall Acton III Bluetooth Home Speaker, Black",
            brandName = "Marshall",
            rating = 4.8f,
            reviewCount = 2114,
            globalRatingsCount = 2117,
            salesTag = "500+ bought in past month",
            imagePlaceholderColors = listOf(0xFF2E3A4F, 0xFF3A3A3A, 0xFF4A4A5A),
            specGroups = listOf(
                SpecGroup("Size", listOf(SpecOption(id = "size_one", label = "One Size"))),
                SpecGroup("Style", listOf(SpecOption(id = "style_acton3", label = "Acton III"))),
                SpecGroup("Color", listOf(
                    SpecOption("v_black", "Black", 0xFF2E3A4F, 199.99, 299.99, 33),
                    SpecOption("v_cream", "Cream", 0xFFF5F0E1, 199.99, 299.99, 33),
                    SpecOption("v_midnight", "Midnight Blue", 0xFF1A237E, 241.99, 299.99, 19)
                ))
            ),
            defaultSpecOptionIds = mapOf("Size" to "size_one", "Style" to "style_acton3", "Color" to "v_black"),
            freeDeliveryDate = "Friday, March 27",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "12 hrs 8 mins",
            installmentMonthly = 33.33,
            installmentMonths = 6,
            reviewSummary = "Customers praise the Marshall Acton III Bluetooth speaker for its excellent sound quality and build quality. Customers appreciate its attractive vintage amplifier design and report that it's easy to connect via Bluetooth. The speaker's compact size makes it perfect for small spaces, and customers find it simple to set up and use.",
            reviewTags = listOf(
                ReviewTag("Sound quality", 358), ReviewTag("Speaker quality", 193),
                ReviewTag("Style", 135), ReviewTag("Bluetooth connectivity", 96),
                ReviewTag("Size", 66), ReviewTag("Value for money", 63),
                ReviewTag("Ease of setup", 50), ReviewTag("Noise level", 42)
            ),
            customerPhotoPlaceholderColors = listOf(0xFF555555, 0xFF777777, 0xFF999999),
            brandLogoPlaceholderColor = 0xFF1A1A1A,
            imageAssetPath = "image/Marshall.jpg"
        )
    }

    private fun createIPhone17ProMax(): ProductDetailData {
        return ProductDetailData(
            id = "prod_001",
            name = "Apple iPhone 17 Pro Max, 256GB, Cosmic Orange - Unlocked",
            brandName = "Apple",
            rating = 4.9f,
            reviewCount = 18234,
            globalRatingsCount = 18500,
            salesTag = "10K+ bought in past month",
            imagePlaceholderColors = listOf(0xFFFF8A65, 0xFFFFAB91, 0xFFFF7043),
            specGroups = listOf(
                SpecGroup("Color", listOf(
                    SpecOption("c_orange", "Cosmic Orange", 0xFFFF8A65),
                    SpecOption("c_blue", "Deep Blue", 0xFF1565C0),
                    SpecOption("c_silver", "Silver", 0xFFBDBDBD)
                )),
                SpecGroup("Storage", listOf(
                    SpecOption("s_256", "256GB", price = 1369.99, originalPrice = 1440.00, discountPercent = 5),
                    SpecOption("s_512", "512GB", price = 1652.31, originalPrice = 1652.31, discountPercent = 0),
                    SpecOption("s_1tb", "1TB", price = 1799.97, originalPrice = 1910.00, discountPercent = 6)
                ))
            ),
            defaultSpecOptionIds = mapOf("Color" to "c_orange", "Storage" to "s_256"),
            freeDeliveryDate = "Friday, March 27",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "9 hrs 32 mins",
            installmentMonthly = 228.33,
            installmentMonths = 6,
            reviewSummary = "Customers love the iPhone 17 Pro Max for its stunning camera quality, exceptional battery life, and the smooth iOS experience. The ProMotion display is frequently praised for its responsiveness. Many users highlight the build quality and premium feel of the device.",
            reviewTags = listOf(
                ReviewTag("Camera quality", 1245), ReviewTag("Battery life", 986),
                ReviewTag("Display quality", 754), ReviewTag("Performance", 623),
                ReviewTag("Build quality", 512), ReviewTag("Value for money", 389)
            ),
            customerPhotoPlaceholderColors = listOf(0xFFFF8A65, 0xFF555555, 0xFF777777),
            brandLogoPlaceholderColor = 0xFF333333,
            imageAssetPath = "image/iPhone.jpg"
        )
    }

    private fun createMacBookAir(): ProductDetailData {
        return ProductDetailData(
            id = "prod_002",
            name = "Apple MacBook Air 13-inch with M4 Chip - Space Gray",
            brandName = "Apple",
            rating = 4.9f,
            reviewCount = 15422,
            globalRatingsCount = 15600,
            salesTag = "5K+ bought in past month",
            imagePlaceholderColors = listOf(0xFF78909C, 0xFF90A4AE, 0xFF607D8B),
            specGroups = listOf(
                SpecGroup("Color", listOf(
                    SpecOption("c_gray", "Space Gray", 0xFF78909C),
                    SpecOption("c_silver", "Silver", 0xFFCFD8DC),
                    SpecOption("c_midnight", "Midnight", 0xFF263238),
                    SpecOption("c_starlight", "Starlight", 0xFFF5E6CC)
                )),
                SpecGroup("Configuration", listOf(
                    SpecOption("cfg_base", "M4 / 16GB / 256GB", price = 1099.00, originalPrice = 1199.00, discountPercent = 8),
                    SpecOption("cfg_mid", "M4 / 16GB / 512GB", price = 1299.00, originalPrice = 1399.00, discountPercent = 7),
                    SpecOption("cfg_high", "M4 / 24GB / 1TB", price = 1699.00, originalPrice = 1799.00, discountPercent = 6)
                ))
            ),
            defaultSpecOptionIds = mapOf("Color" to "c_gray", "Configuration" to "cfg_base"),
            freeDeliveryDate = "Saturday, March 28",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "7 hrs 15 mins",
            installmentMonthly = 183.17,
            installmentMonths = 6,
            reviewSummary = "Customers are impressed with the MacBook Air's lightweight design, exceptional battery life, and silent fanless operation. The M4 chip delivers outstanding performance for everyday tasks. The Liquid Retina display receives consistent praise for color accuracy.",
            reviewTags = listOf(
                ReviewTag("Battery life", 1102), ReviewTag("Performance", 876),
                ReviewTag("Display quality", 654), ReviewTag("Portability", 543),
                ReviewTag("Build quality", 421), ReviewTag("Value for money", 356)
            ),
            customerPhotoPlaceholderColors = listOf(0xFF78909C, 0xFF607D8B, 0xFF90A4AE),
            brandLogoPlaceholderColor = 0xFF333333,
            imageAssetPath = "image/MacBook.jpg"
        )
    }

    private fun createSonyWH1000XM5(): ProductDetailData {
        return ProductDetailData(
            id = "prod_004",
            name = "Sony WH-1000XM5 Wireless Industry Leading Noise Canceling Headphones",
            brandName = "Sony",
            rating = 4.8f,
            reviewCount = 9786,
            globalRatingsCount = 9900,
            salesTag = "2K+ bought in past month",
            imagePlaceholderColors = listOf(0xFF212121, 0xFF424242, 0xFF616161),
            specGroups = listOf(
                SpecGroup("Color", listOf(
                    SpecOption("c_black", "Black", 0xFF212121, 399.99, 429.99, 7),
                    SpecOption("c_silver", "Silver", 0xFFBDBDBD, 399.99, 429.99, 7),
                    SpecOption("c_blue", "Midnight Blue", 0xFF1A237E, 399.99, 429.99, 7)
                ))
            ),
            defaultSpecOptionIds = mapOf("Color" to "c_black"),
            freeDeliveryDate = "Friday, March 27",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "11 hrs 45 mins",
            installmentMonthly = 66.67,
            installmentMonths = 6,
            reviewSummary = "Customers praise the Sony WH-1000XM5 for its industry-leading noise cancellation and exceptional sound quality. The lightweight design and comfortable fit make them perfect for extended listening sessions. Many users appreciate the multipoint Bluetooth connectivity.",
            reviewTags = listOf(
                ReviewTag("Noise cancellation", 892), ReviewTag("Sound quality", 756),
                ReviewTag("Comfort", 634), ReviewTag("Battery life", 521),
                ReviewTag("Build quality", 345), ReviewTag("Value for money", 298)
            ),
            customerPhotoPlaceholderColors = listOf(0xFF212121, 0xFF555555, 0xFF888888),
            brandLogoPlaceholderColor = 0xFF000000,
            imageAssetPath = "image/Sony.jpg"
        )
    }

    private fun createNintendoSwitch(): ProductDetailData {
        return ProductDetailData(
            id = "prod_005",
            name = "Nintendo Switch OLED Model - White",
            brandName = "Nintendo",
            rating = 4.9f,
            reviewCount = 21340,
            globalRatingsCount = 21500,
            salesTag = "8K+ bought in past month",
            imagePlaceholderColors = listOf(0xFFE0E0E0, 0xFFEEEEEE, 0xFFF5F5F5),
            specGroups = listOf(
                SpecGroup("Color", listOf(
                    SpecOption("c_white", "White", 0xFFE0E0E0, 349.99, 359.99, 3),
                    SpecOption("c_neon", "Neon Blue/Red", 0xFF1565C0, 349.99, 359.99, 3)
                ))
            ),
            defaultSpecOptionIds = mapOf("Color" to "c_white"),
            freeDeliveryDate = "Friday, March 27",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "10 hrs 20 mins",
            installmentMonthly = 58.33,
            installmentMonths = 6,
            reviewSummary = "Customers love the Nintendo Switch OLED for its vibrant display upgrade and versatile handheld-to-TV gameplay. The enhanced kickstand and improved audio speakers are frequently praised. The game library continues to receive high marks from users of all ages.",
            reviewTags = listOf(
                ReviewTag("Display quality", 1523), ReviewTag("Game selection", 1245),
                ReviewTag("Portability", 986), ReviewTag("Build quality", 754),
                ReviewTag("Value for money", 632), ReviewTag("Battery life", 421)
            ),
            customerPhotoPlaceholderColors = listOf(0xFFE0E0E0, 0xFF1565C0, 0xFF888888),
            brandLogoPlaceholderColor = 0xFFE60012,
            imageAssetPath = "image/Nintendo.jpg"
        )
    }

    private fun createSamsungGalaxyWatch7(): ProductDetailData {
        return ProductDetailData(
            id = "prod_006",
            name = "Samsung Galaxy Watch7 with Galaxy AI, 44mm, Bluetooth, Green",
            brandName = "Samsung",
            rating = 4.7f,
            reviewCount = 5231,
            globalRatingsCount = 5300,
            salesTag = "1K+ bought in past month",
            imagePlaceholderColors = listOf(0xFF2E7D32, 0xFF388E3C, 0xFF43A047),
            specGroups = listOf(
                SpecGroup("Size", listOf(
                    SpecOption("s_40", "40mm", price = 279.99, originalPrice = 299.99, discountPercent = 7),
                    SpecOption("s_44", "44mm", price = 299.99, originalPrice = 329.99, discountPercent = 9)
                )),
                SpecGroup("Color", listOf(
                    SpecOption("c_green", "Green", 0xFF2E7D32),
                    SpecOption("c_silver", "Silver", 0xFFBDBDBD),
                    SpecOption("c_cream", "Cream", 0xFFF5F0E1)
                ))
            ),
            defaultSpecOptionIds = mapOf("Size" to "s_44", "Color" to "c_green"),
            freeDeliveryDate = "Saturday, March 28",
            fastestDeliveryDate = "Wednesday, March 25",
            countdownText = "6 hrs 50 mins",
            installmentMonthly = 50.00,
            installmentMonths = 6,
            reviewSummary = "Customers appreciate the Galaxy Watch7's comprehensive health tracking features powered by Galaxy AI. The sleep tracking and heart rate monitoring receive high marks. Users praise the smooth Wear OS experience and the bright, responsive display.",
            reviewTags = listOf(
                ReviewTag("Health tracking", 456), ReviewTag("Display", 378),
                ReviewTag("Battery life", 312), ReviewTag("Comfort", 267),
                ReviewTag("Galaxy AI features", 198), ReviewTag("Value for money", 165)
            ),
            customerPhotoPlaceholderColors = listOf(0xFF2E7D32, 0xFF555555, 0xFF888888),
            brandLogoPlaceholderColor = 0xFF1428A0,
            imageAssetPath = "image/Samsung.jpg"
        )
    }

    private fun createDysonV15(): ProductDetailData {
        return ProductDetailData(
            id = "prod_007",
            name = "Dyson V15 Detect Absolute Cordless Vacuum Cleaner",
            brandName = "Dyson",
            rating = 4.7f,
            reviewCount = 8876,
            globalRatingsCount = 9000,
            salesTag = "3K+ bought in past month",
            imagePlaceholderColors = listOf(0xFFFF6F00, 0xFFFFAB40, 0xFFFFC107),
            specGroups = listOf(
                SpecGroup("Model", listOf(
                    SpecOption("m_detect", "V15 Detect", price = 649.99, originalPrice = 749.99, discountPercent = 13),
                    SpecOption("m_absolute", "V15 Detect Absolute", price = 749.99, originalPrice = 849.99, discountPercent = 12)
                ))
            ),
            defaultSpecOptionIds = mapOf("Model" to "m_detect"),
            freeDeliveryDate = "Friday, March 27",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "8 hrs 33 mins",
            installmentMonthly = 108.33,
            installmentMonths = 6,
            reviewSummary = "Customers are impressed with the Dyson V15's powerful suction and innovative laser dust detection technology. The LCD screen showing particle counts is a standout feature. Users praise the versatile attachments and the overall cleaning performance on both hard floors and carpets.",
            reviewTags = listOf(
                ReviewTag("Suction power", 723), ReviewTag("Laser detection", 534),
                ReviewTag("Battery life", 467), ReviewTag("Build quality", 389),
                ReviewTag("Ease of use", 312), ReviewTag("Value for money", 245)
            ),
            customerPhotoPlaceholderColors = listOf(0xFFFF6F00, 0xFF666666, 0xFF999999),
            brandLogoPlaceholderColor = 0xFF6C0AAB,
            imageAssetPath = "image/Dyson.jpg"
        )
    }

    private fun createNespresso(): ProductDetailData {
        return ProductDetailData(
            id = "prod_008",
            name = "Nespresso Vertuo Pop+ Deluxe Coffee and Espresso Machine by De'Longhi",
            brandName = "Nespresso",
            rating = 4.6f,
            reviewCount = 4382,
            globalRatingsCount = 4500,
            salesTag = "1K+ bought in past month",
            imagePlaceholderColors = listOf(0xFF5D4037, 0xFF795548, 0xFF8D6E63),
            specGroups = listOf(
                SpecGroup("Color", listOf(
                    SpecOption("c_black", "Black", 0xFF212121, 129.99, 169.99, 24),
                    SpecOption("c_red", "Red", 0xFFC62828, 129.99, 169.99, 24),
                    SpecOption("c_mint", "Mint", 0xFF80CBC4, 139.99, 169.99, 18)
                ))
            ),
            defaultSpecOptionIds = mapOf("Color" to "c_black"),
            freeDeliveryDate = "Saturday, March 28",
            fastestDeliveryDate = "Wednesday, March 25",
            countdownText = "5 hrs 12 mins",
            installmentMonthly = null,
            installmentMonths = null,
            reviewSummary = "Customers enjoy the Nespresso Vertuo Pop+ for its compact design and ease of use. The one-touch brewing system and quick heat-up time are frequently praised. Users appreciate the variety of coffee sizes and the consistent crema quality.",
            reviewTags = listOf(
                ReviewTag("Ease of use", 345), ReviewTag("Coffee quality", 289),
                ReviewTag("Compact design", 234), ReviewTag("Speed", 178),
                ReviewTag("Value for money", 156), ReviewTag("Noise level", 98)
            ),
            customerPhotoPlaceholderColors = listOf(0xFF5D4037, 0xFF888888),
            brandLogoPlaceholderColor = 0xFF000000,
            imageAssetPath = "image/Nespresso.jpg"
        )
    }

    private fun createKitchenAid(): ProductDetailData {
        return ProductDetailData(
            id = "prod_009",
            name = "KitchenAid Artisan Series 5-Quart Tilt-Head Stand Mixer",
            brandName = "KitchenAid",
            rating = 4.9f,
            reviewCount = 14620,
            globalRatingsCount = 14800,
            salesTag = "5K+ bought in past month",
            imagePlaceholderColors = listOf(0xFFD32F2F, 0xFFE53935, 0xFFEF5350),
            specGroups = listOf(
                SpecGroup("Color", listOf(
                    SpecOption("c_red", "Empire Red", 0xFFD32F2F, 449.99, 499.99, 10),
                    SpecOption("c_silver", "Contour Silver", 0xFFBDBDBD, 449.99, 499.99, 10),
                    SpecOption("c_black", "Onyx Black", 0xFF212121, 449.99, 499.99, 10),
                    SpecOption("c_blue", "Blue Velvet", 0xFF1565C0, 469.99, 499.99, 6)
                ))
            ),
            defaultSpecOptionIds = mapOf("Color" to "c_red"),
            freeDeliveryDate = "Friday, March 27",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "10 hrs 5 mins",
            installmentMonthly = 75.00,
            installmentMonths = 6,
            reviewSummary = "Customers love the KitchenAid Artisan mixer for its powerful motor and versatile functionality. The tilt-head design makes it easy to add ingredients, and the 5-quart capacity is perfect for most recipes. Users consistently praise its durability and the wide range of available attachments.",
            reviewTags = listOf(
                ReviewTag("Build quality", 1234), ReviewTag("Power", 986),
                ReviewTag("Versatility", 754), ReviewTag("Ease of use", 623),
                ReviewTag("Design", 512), ReviewTag("Value for money", 445)
            ),
            customerPhotoPlaceholderColors = listOf(0xFFD32F2F, 0xFF888888, 0xFF666666),
            brandLogoPlaceholderColor = 0xFF000000,
            imageAssetPath = "image/KitchenAid.jpg"
        )
    }

    private fun createVitamix(): ProductDetailData {
        return ProductDetailData(
            id = "prod_010",
            name = "Vitamix Explorian Blender, Professional-Grade, 64 oz Container",
            brandName = "Vitamix",
            rating = 4.8f,
            reviewCount = 7864,
            globalRatingsCount = 8000,
            salesTag = "2K+ bought in past month",
            imagePlaceholderColors = listOf(0xFF212121, 0xFF424242, 0xFF616161),
            specGroups = listOf(
                SpecGroup("Color", listOf(
                    SpecOption("c_black", "Black", 0xFF212121, 349.95, 449.95, 22),
                    SpecOption("c_red", "Red", 0xFFC62828, 349.95, 449.95, 22),
                    SpecOption("c_white", "White", 0xFFE0E0E0, 369.95, 449.95, 18)
                ))
            ),
            defaultSpecOptionIds = mapOf("Color" to "c_black"),
            freeDeliveryDate = "Saturday, March 28",
            fastestDeliveryDate = "Wednesday, March 25",
            countdownText = "4 hrs 28 mins",
            installmentMonthly = 58.33,
            installmentMonths = 6,
            reviewSummary = "Customers praise the Vitamix Explorian for its powerful blending capability and professional-grade performance. The variable speed control allows precise texture management. Users love how it handles everything from smoothies to hot soups with ease.",
            reviewTags = listOf(
                ReviewTag("Blending power", 654), ReviewTag("Durability", 523),
                ReviewTag("Ease of cleaning", 412), ReviewTag("Versatility", 378),
                ReviewTag("Noise level", 234), ReviewTag("Value for money", 198)
            ),
            customerPhotoPlaceholderColors = listOf(0xFF212121, 0xFF777777),
            brandLogoPlaceholderColor = 0xFF000000,
            imageAssetPath = "image/Vitamix.jpg"
        )
    }

    private fun createNikePegasus(): ProductDetailData {
        return ProductDetailData(
            id = "prod_011",
            name = "Nike Air Zoom Pegasus 41 Men's Road Running Shoes",
            brandName = "Nike",
            rating = 4.7f,
            reviewCount = 6654,
            globalRatingsCount = 6800,
            salesTag = "3K+ bought in past month",
            imagePlaceholderColors = listOf(0xFF1565C0, 0xFF42A5F5, 0xFF90CAF9),
            specGroups = listOf(
                SpecGroup("Size", listOf(
                    SpecOption("sz_8", "US 8"), SpecOption("sz_9", "US 9"),
                    SpecOption("sz_10", "US 10"), SpecOption("sz_11", "US 11"),
                    SpecOption("sz_12", "US 12")
                )),
                SpecGroup("Color", listOf(
                    SpecOption("c_blue", "Blue/White", 0xFF1565C0, 139.99, 139.99, 0),
                    SpecOption("c_black", "Black/White", 0xFF212121, 139.99, 139.99, 0),
                    SpecOption("c_grey", "Wolf Grey", 0xFF9E9E9E, 139.99, 139.99, 0)
                ))
            ),
            defaultSpecOptionIds = mapOf("Size" to "sz_10", "Color" to "c_blue"),
            freeDeliveryDate = "Friday, March 27",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "9 hrs 15 mins",
            installmentMonthly = null,
            installmentMonths = null,
            reviewSummary = "Customers love the Nike Pegasus 41 for its responsive cushioning and reliable comfort during daily runs. The React foam and Zoom Air unit provide excellent energy return. Users appreciate the breathable upper and true-to-size fit.",
            reviewTags = listOf(
                ReviewTag("Comfort", 567), ReviewTag("Cushioning", 445),
                ReviewTag("Fit", 389), ReviewTag("Durability", 312),
                ReviewTag("Style", 256), ReviewTag("Value for money", 198)
            ),
            customerPhotoPlaceholderColors = listOf(0xFF1565C0, 0xFF888888, 0xFF555555),
            brandLogoPlaceholderColor = 0xFF000000,
            imageAssetPath = "image/Nike.jpg"
        )
    }

    private fun createAdidasSoccerBall(): ProductDetailData {
        return ProductDetailData(
            id = "prod_012",
            name = "adidas Tiro League Thermally Bonded Soccer Ball",
            brandName = "adidas",
            rating = 4.8f,
            reviewCount = 3541,
            globalRatingsCount = 3600,
            salesTag = "800+ bought in past month",
            imagePlaceholderColors = listOf(0xFFFFFFFF, 0xFFF5F5F5, 0xFFEEEEEE),
            specGroups = listOf(
                SpecGroup("Size", listOf(
                    SpecOption("sz_4", "Size 4", price = 34.99, originalPrice = 39.99, discountPercent = 13),
                    SpecOption("sz_5", "Size 5", price = 39.99, originalPrice = 45.00, discountPercent = 11)
                ))
            ),
            defaultSpecOptionIds = mapOf("Size" to "sz_5"),
            freeDeliveryDate = "Friday, March 27",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "11 hrs 40 mins",
            installmentMonthly = null,
            installmentMonths = null,
            reviewSummary = "Customers appreciate the adidas Tiro League ball for its excellent quality and consistent performance. The thermally bonded construction provides a seamless feel and reliable flight. Users praise the durability and the ball's responsiveness during play.",
            reviewTags = listOf(
                ReviewTag("Quality", 312), ReviewTag("Durability", 256),
                ReviewTag("Flight consistency", 189), ReviewTag("Touch", 145),
                ReviewTag("Value for money", 123), ReviewTag("Design", 98)
            ),
            customerPhotoPlaceholderColors = listOf(0xFFE0E0E0, 0xFF888888),
            brandLogoPlaceholderColor = 0xFF000000,
            imageAssetPath = "image/Adidas.jpg"
        )
    }

    private fun createWilsonBasketball(): ProductDetailData {
        return ProductDetailData(
            id = "prod_013",
            name = "Wilson Evolution Indoor Game Basketball, Official Size 7",
            brandName = "Wilson",
            rating = 4.9f,
            reviewCount = 5933,
            globalRatingsCount = 6000,
            salesTag = "2K+ bought in past month",
            imagePlaceholderColors = listOf(0xFFE65100, 0xFFBF360C, 0xFFFF6D00),
            specGroups = listOf(
                SpecGroup("Size", listOf(
                    SpecOption("sz_6", "Size 6 (28.5\")", price = 69.95, originalPrice = 79.95, discountPercent = 13),
                    SpecOption("sz_7", "Size 7 (29.5\")", price = 79.95, originalPrice = 89.95, discountPercent = 11)
                ))
            ),
            defaultSpecOptionIds = mapOf("Size" to "sz_7"),
            freeDeliveryDate = "Friday, March 27",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "8 hrs 22 mins",
            installmentMonthly = null,
            installmentMonths = null,
            reviewSummary = "Customers consider the Wilson Evolution the gold standard for indoor basketballs. The microfiber composite leather cover provides exceptional grip and a soft, broken-in feel right out of the box. Users consistently praise the ball's consistent bounce and superior handling.",
            reviewTags = listOf(
                ReviewTag("Grip", 523), ReviewTag("Bounce consistency", 412),
                ReviewTag("Durability", 345), ReviewTag("Feel", 298),
                ReviewTag("Value for money", 234), ReviewTag("Indoor performance", 178)
            ),
            customerPhotoPlaceholderColors = listOf(0xFFE65100, 0xFF888888),
            brandLogoPlaceholderColor = 0xFF002B5C,
            imageAssetPath = "image/Wilson.jpg"
        )
    }

    private fun createStarbucksCoffee(): ProductDetailData {
        return ProductDetailData(
            id = "prod_014",
            name = "Starbucks Pike Place Roast, Whole Bean Coffee, Medium Roast, 28 oz",
            brandName = "Starbucks",
            rating = 4.8f,
            reviewCount = 12455,
            globalRatingsCount = 12600,
            salesTag = "5K+ bought in past month",
            imagePlaceholderColors = listOf(0xFF33691E, 0xFF558B2F, 0xFF689F38),
            specGroups = listOf(
                SpecGroup("Size", listOf(
                    SpecOption("sz_12", "12 oz", price = 9.99, originalPrice = 11.99, discountPercent = 17),
                    SpecOption("sz_28", "28 oz", price = 14.99, originalPrice = 18.99, discountPercent = 21),
                    SpecOption("sz_40", "40 oz", price = 22.99, originalPrice = 27.99, discountPercent = 18)
                ))
            ),
            defaultSpecOptionIds = mapOf("Size" to "sz_28"),
            freeDeliveryDate = "Friday, March 27",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "7 hrs 55 mins",
            installmentMonthly = null,
            installmentMonths = null,
            reviewSummary = "Customers love the Starbucks Pike Place Roast for its smooth, well-balanced flavor and rich aroma. The medium roast profile is praised for being approachable yet flavorful. Many users appreciate the consistency and freshness of the whole bean format.",
            reviewTags = listOf(
                ReviewTag("Flavor", 1023), ReviewTag("Aroma", 756),
                ReviewTag("Freshness", 534), ReviewTag("Value for money", 412),
                ReviewTag("Smooth taste", 345), ReviewTag("Consistency", 267)
            ),
            customerPhotoPlaceholderColors = listOf(0xFF33691E, 0xFF888888),
            brandLogoPlaceholderColor = 0xFF00704A,
            imageAssetPath = "image/Starbucks.jpg"
        )
    }

    private fun createCocaCola(): ProductDetailData {
        return ProductDetailData(
            id = "prod_015",
            name = "Coca-Cola Zero Sugar, 12 fl oz Cans, 24-Pack",
            brandName = "Coca-Cola",
            rating = 4.8f,
            reviewCount = 10234,
            globalRatingsCount = 10400,
            salesTag = "10K+ bought in past month",
            imagePlaceholderColors = listOf(0xFFB71C1C, 0xFFC62828, 0xFFD32F2F),
            specGroups = listOf(
                SpecGroup("Pack Size", listOf(
                    SpecOption("pk_12", "12-Pack", price = 9.99, originalPrice = 11.99, discountPercent = 17),
                    SpecOption("pk_24", "24-Pack", price = 16.99, originalPrice = 19.99, discountPercent = 15),
                    SpecOption("pk_36", "36-Pack", price = 24.99, originalPrice = 29.99, discountPercent = 17)
                ))
            ),
            defaultSpecOptionIds = mapOf("Pack Size" to "pk_24"),
            freeDeliveryDate = "Saturday, March 28",
            fastestDeliveryDate = "Wednesday, March 25",
            countdownText = "5 hrs 18 mins",
            installmentMonthly = null,
            installmentMonths = null,
            reviewSummary = "Customers enjoy the Coca-Cola Zero Sugar for its great taste with zero calories. Many users say it's the closest sugar-free option to original Coca-Cola. The convenient 12 oz cans and bulk pricing are frequently praised.",
            reviewTags = listOf(
                ReviewTag("Taste", 876), ReviewTag("Value for money", 654),
                ReviewTag("Zero sugar", 523), ReviewTag("Convenience", 412),
                ReviewTag("Freshness", 289), ReviewTag("Packaging", 178)
            ),
            customerPhotoPlaceholderColors = listOf(0xFFB71C1C, 0xFF888888),
            brandLogoPlaceholderColor = 0xFFB71C1C,
            imageAssetPath = "image/Coca-Cola.jpg"
        )
    }

    private fun createOreo(): ProductDetailData {
        return ProductDetailData(
            id = "prod_016",
            name = "OREO Original Chocolate Sandwich Cookies, Family Size, 18.12 oz",
            brandName = "OREO",
            rating = 4.9f,
            reviewCount = 17328,
            globalRatingsCount = 17500,
            salesTag = "10K+ bought in past month",
            imagePlaceholderColors = listOf(0xFF1A237E, 0xFF283593, 0xFF303F9F),
            specGroups = listOf(
                SpecGroup("Flavor", listOf(
                    SpecOption("f_original", "Original", price = 5.99, originalPrice = 6.99, discountPercent = 14),
                    SpecOption("f_double", "Double Stuf", price = 5.99, originalPrice = 6.99, discountPercent = 14),
                    SpecOption("f_golden", "Golden", price = 5.99, originalPrice = 6.99, discountPercent = 14),
                    SpecOption("f_mint", "Mint", price = 6.49, originalPrice = 7.49, discountPercent = 13)
                ))
            ),
            defaultSpecOptionIds = mapOf("Flavor" to "f_original"),
            freeDeliveryDate = "Friday, March 27",
            fastestDeliveryDate = "Tomorrow, March 23",
            countdownText = "9 hrs 42 mins",
            installmentMonthly = null,
            installmentMonths = null,
            reviewSummary = "Customers love OREO cookies for their classic flavor combination of chocolate wafers and cream filling. The family size offers excellent value. Many users appreciate the resealable packaging that keeps cookies fresh.",
            reviewTags = listOf(
                ReviewTag("Taste", 1456), ReviewTag("Value for money", 1023),
                ReviewTag("Freshness", 756), ReviewTag("Classic flavor", 623),
                ReviewTag("Packaging", 412), ReviewTag("Portion size", 289)
            ),
            customerPhotoPlaceholderColors = listOf(0xFF1A237E, 0xFF888888),
            brandLogoPlaceholderColor = 0xFF004BA0,
            imageAssetPath = "image/OREO.jpg"
        )
    }
}