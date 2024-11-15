-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 15, 2024 at 08:50 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stylize`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `cart_id` bigint(20) UNSIGNED NOT NULL,
  `product_id` int(11) NOT NULL,
  `added_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `quantity` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `size` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`cart_id`, `product_id`, `added_at`, `quantity`, `id`, `size`) VALUES
(16, 99990001, '2024-11-14 15:46:34', 2, 2024000001, 'S'),
(17, 99990011, '2024-11-14 15:46:39', 3, 2024000001, 'One Size'),
(18, 99990030, '2024-11-14 15:49:47', 30, 2024000001, 'L'),
(19, 99990030, '2024-11-14 15:58:50', 5, 2024000001, 'S'),
(20, 99990045, '2024-11-14 15:59:46', 2, 2024000001, 'S'),
(21, 99990007, '2024-11-14 16:00:30', 4, 2024000001, 'One Size'),
(22, 99990044, '2024-11-14 16:20:48', 3, 2024000001, 'S'),
(23, 99990004, '2024-11-14 16:20:56', 3, 2024000001, 'S'),
(24, 99990000, '2024-11-14 16:23:16', 2, 2024000001, 'S'),
(25, 99990008, '2024-11-14 16:33:05', 24, 2024000001, 'S'),
(29, 99990030, '2024-11-14 17:51:32', 1, 2024000003, 'L'),
(31, 99990044, '2024-11-14 17:57:19', 3, 2024000004, 'S'),
(32, 99990001, '2024-11-14 18:32:00', 1, 2024000004, 'S'),
(33, 99990027, '2024-11-15 05:06:17', 1, 2024000004, 'S'),
(34, 99990045, '2024-11-15 05:13:34', 2, 2024000004, 'S');

--
-- Triggers `cart`
--
DELIMITER $$
CREATE TRIGGER `quantity_check_before_insert` BEFORE INSERT ON `cart` FOR EACH ROW BEGIN
    IF NEW.quantity <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantity must be greater than 0';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `tag` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `discount` decimal(5,2) DEFAULT 0.00,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `sizes` varchar(255) DEFAULT NULL,
  `gender` enum('Men','Women','Unisex') DEFAULT 'Unisex',
  `images` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `name`, `description`, `tag`, `type`, `price`, `discount`, `created_at`, `updated_at`, `sizes`, `gender`, `images`) VALUES
(99990000, 'Men\'s T-shirt', 'Comfortable cotton t-shirt for men in various colors.', 'Sale', 'Clothing', 19.99, 10.00, '2024-11-12 10:52:01', '2024-11-14 17:02:11', 'S, M, L, XL', 'Men', 'https://i5.walmartimages.com/asr/8673e968-19ce-404c-abe7-0c4fa608bacf_1.2d78b914a4e46c3db69cd50ba71bcc80.jpeg'),
(99990001, 'Women\'s Frock', 'Elegant summer dress with floral design, perfect for casual outings.', 'New Arrival', 'Clothing', 39.99, 15.00, '2024-11-12 10:52:01', '2024-11-15 05:18:11', 'S, M, L, XL', 'Women', 'https://i.pinimg.com/originals/e3/3e/ce/e33eceb5cc40da6bcd280bd555e5e09c.jpg'),
(99990002, 'Casual Hoodie', 'Cozy hoodie with a simple design for both men and women.', 'Sale', 'Clothing', 29.99, 5.00, '2024-11-12 10:52:01', '2024-11-14 15:20:14', 'S, M, L, XL, XXL', 'Unisex', 'https://yoode.com/cdn/shop/files/men-cozy-hoodie-color-navyblue_1.webp?v=1706154959&width=535'),
(99990003, 'Men\'s Jeans', 'Classic denim jeans with a comfortable fit.', 'Classic', 'Clothing', 49.99, 0.00, '2024-11-12 10:52:01', '2024-11-13 16:37:15', '30, 32, 34, 36, 38', 'Men', 'https://images-na.ssl-images-amazon.com/images/I/415xbv1BcvL.jpg'),
(99990004, 'Pink Women\'s Sweater', 'Warm wool sweater ideal for the colder months.', 'New Arrival', 'Clothing', 59.99, 20.00, '2024-11-12 10:52:01', '2024-11-15 05:15:59', 'S, M, L, XL', 'Women', 'https://content.woolovers.com/img/o/f865f-21336_q20l_peonypink_w_1.jpg'),
(99990005, 'Men\'s Sneakers', 'Stylish and comfortable sneakers for men.', 'Sale', 'Footwear', 59.99, 10.00, '2024-11-12 10:52:01', '2024-11-13 16:38:27', '7, 8, 9, 10, 11', 'Men', 'https://i.pinimg.com/736x/af/75/b6/af75b61357577a5121d4c27356e67db9.jpg'),
(99990006, 'Women\'s Blouse', 'Chic blouse with a relaxed fit for everyday wear.', 'Classic', 'Clothing', 24.99, 5.00, '2024-11-12 10:52:01', '2024-11-13 16:39:19', 'S, M, L, XL', 'Women', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcReNJHBFra60mCJhOxGup6-dkYPkX_FvgYaGg&s'),
(99990007, 'Unisex Backpack', 'Durable and spacious backpack, great for travel or school.', 'New Arrival', 'Accessories', 39.99, 0.00, '2024-11-12 10:52:01', '2024-11-13 16:40:16', 'One Size', 'Unisex', 'https://images-cdn.ubuy.co.in/64c6ab2c41ff6b6a2005e9b1-wen-four-compartment-heavy-duty-backpack.jpg'),
(99990008, 'Long Women\'s Skirt', 'A fashionable skirt with a trendy design.', 'Sale', 'Clothing', 34.99, 10.00, '2024-11-12 10:52:01', '2024-11-15 05:21:23', 'S, M, L, XL', 'Women', 'https://i.pinimg.com/736x/9c/b7/13/9cb713a3afeff8e1ea789368e579549c.jpg'),
(99990009, 'Men\'s Polo Shirt', 'Casual polo shirt for men with a slim fit.', 'Classic', 'Clothing', 25.99, 0.00, '2024-11-12 10:52:01', '2024-11-13 16:41:10', 'S, M, L, XL', 'Men', 'https://m.media-amazon.com/images/I/71DmAyqDSdL._AC_UY350_.jpg'),
(99990010, 'Women\'s Leather Jacket', 'Premium leather jacket designed for women.', 'New Arrival', 'Clothing', 199.99, 25.00, '2024-11-12 10:52:01', '2024-11-13 16:41:38', 'S, M, L, XL', 'Women', 'https://leatherretail.in/cdn/shop/files/81Pbgnq1E3L._SL1500__1_491decbc-f0d2-41ee-8ec6-fab04c918956.jpg?v=1712917783&width=1445'),
(99990011, 'Unisex Sunglasses', 'Stylish sunglasses suitable for both men and women.', 'Sale', 'Accessories', 15.99, 0.00, '2024-11-12 10:52:01', '2024-11-13 16:42:03', 'One Size', 'Unisex', 'https://rukminim2.flixcart.com/image/850/1000/xif0q/sunglass/t/r/k/free-size-clubmaster-black-unisex-gala-style-fashion-original-imaggzvrz8jvev3m.jpeg?q=20&crop=false'),
(99990012, 'Men\'s Watch', 'Classic men\'s wristwatch with a leather strap.', 'Sale', 'Accessories', 129.99, 10.00, '2024-11-12 10:52:01', '2024-11-13 16:44:29', 'One Size', 'Men', 'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcRnjbDFIAkJrC9236N0VW_seTP70Ac6pmS-RC19q8MA89-p3fzd5dZwcyPHzK2UBkBHuL9yxtTIxYUY9wbU6I_c8ZV7XhlbeKK0diSxeuodc9HojGV8ZBO5'),
(99990013, 'Women\'s Scarf', 'Soft cashmere scarf in multiple colors.', 'Classic', 'Accessories', 19.99, 5.00, '2024-11-12 10:52:01', '2024-11-13 16:43:39', 'One Size', 'Women', 'https://i.pinimg.com/736x/93/12/df/9312dfa3a70bc61fb4cd1e784416c2d0.jpg'),
(99990014, 'Unisex Cap', 'Comfortable cap with adjustable fit for all genders.', 'Sale', 'Accessories', 14.99, 0.00, '2024-11-12 10:52:01', '2024-11-13 16:46:26', 'One Size', 'Unisex', 'https://www.jiomart.com/images/product/original/rv8fahlx4y/fancy-cap-f-logo-cap-for-men-and-women-strap-closure-adjustable-hat-for-all-outdoor-indoor-activities-freesize-black-product-images-rv8fahlx4y-0-202406011404.jpg?im=Resize=(1000,1000)'),
(99990015, 'Men\'s Running Shoes', 'Lightweight and breathable shoes for running.', 'New Arrival', 'Footwear', 89.99, 15.00, '2024-11-12 10:52:01', '2024-11-13 16:47:07', '8, 9, 10, 11, 12', 'Men', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvzBRl6tgQAcTw-x2FeFuNkTgu5gIGM5v6yg&s'),
(99990016, 'Women\'s Sandals', 'Stylish summer sandals for women.', 'Sale', 'Footwear', 29.99, 10.00, '2024-11-12 10:52:01', '2024-11-13 16:47:41', '6, 7, 8, 9, 10', 'Women', 'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcT8OFc88AZZJsi2FAgIgm9Cm7XJNj09JPY-nsCOb3ef0g0B66BVFp-IihBbDRxw18EO_c9Shvy1AsS-G8DAzWwrY-aER4vRpigcX13yUFDPsOY_6YONcrai2tE'),
(99990017, 'Unisex Watch', 'Minimalist design watch suitable for both men and women.', 'Classic', 'Accessories', 49.99, 5.00, '2024-11-12 10:52:01', '2024-11-13 16:48:32', 'One Size', 'Unisex', 'https://rukminim2.flixcart.com/image/1200/1200/xif0q/watch/b/s/l/-original-imah2ggw2kakjpyq.jpeg'),
(99990018, 'Men\'s Chinos', 'Comfortable chinos with a slim fit for a smart look.', 'Classic', 'Clothing', 39.99, 0.00, '2024-11-12 10:52:01', '2024-11-13 17:10:15', '30, 32, 34, 36', 'Men', 'https://images.meesho.com/images/products/437530967/vb7t5_1200.jpg'),
(99990019, 'Women\'s Leggings', 'Stretchable leggings designed for comfort and style.', 'Sale', 'Clothing', 19.99, 0.00, '2024-11-12 10:52:01', '2024-11-13 16:57:01', 'S, M, L, XL', 'Women', 'https://images.meesho.com/images/products/450685311/rjxpv_1200.jpg'),
(99990020, 'Unisex Sports Shoes', 'High-performance sports shoes for men and women.', 'New Arrival', 'Footwear', 69.99, 20.00, '2024-11-12 10:52:01', '2024-11-13 16:57:17', '7, 8, 9, 10, 11, 12', 'Unisex', 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcQgk4Zx9hAJljF3DhMy3PwO9HI5UhgJBwXUBOjiLPhv5I3Ou-omwuZWTvhqpkKYFXgTjwz9sVKCGFGOQ-QjWxDgB7S1u2HaN89WttpgfFULooJ33EwyBSqI'),
(99990021, 'Women\'s Blazer', 'Professional blazer for women, perfect for office wear.', 'Classic', 'Clothing', 79.99, 10.00, '2024-11-12 10:52:01', '2024-11-13 16:57:59', 'S, M, L, XL', 'Women', 'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcQYUAXMpXK_miRL3qACayxBJQUImusbZMTiVyZPnFPg4_vNd8NSpN1VgROc7PzujHdnuj8Fuqi1haA1oxHD4DwNPKVKXUZI8puU4AmHSYGd'),
(99990022, 'Men\'s Jacket', 'Casual winter jacket with a warm lining.', 'Sale', 'Clothing', 89.99, 15.00, '2024-11-12 10:52:01', '2024-11-13 16:58:40', 'S, M, L, XL', 'Men', 'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcTJfXVVgZvV9Xfu98Nga8c4MGQMmSCOSqCgyC9HQwqkdVyDqODS-cljmnBuYFA9kvAGAx-5pDbUT6ovasszOfp5qVEs0omg6Xt2ciuXMuSjHil2l1qMzRX0'),
(99990023, 'Unisex T-shirt', 'Basic cotton t-shirt for casual wear, suitable for both men and women.', 'Classic', 'Clothing', 14.99, 0.00, '2024-11-12 10:52:01', '2024-11-13 17:08:55', 'S, M, L, XL', 'Unisex', 'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSZi53I5Ys8lKJOZ8BkjBbOsaFKrR1amKtOZ4L2gOUy5w5lp4lJbRQ5fj_xVOZ5DOYJdKH_Wz5sSCVSFK0DQPBOn9p46tLpZ4mKd-m3LvM'),
(99990024, 'Women\'s Coat', 'Warm and stylish wool coat for the winter season.', 'New Arrival', 'Clothing', 149.99, 20.00, '2024-11-12 10:52:01', '2024-11-13 17:03:43', 'S, M, L, XL', 'Women', 'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcQdahVfmNj75cADQkdddBWZZGs7cyISPevcjYul7oLE9ewFkPLrnKV8sOvYbtz0LMbTzkIK3D-Jm2Rja8_9bJu-rT3t0oFIQ632KeW2AsmnVg-5dqCEJ-QAIQ'),
(99990025, 'Men\'s Shorts', 'Comfortable and breathable shorts for the summer.', 'Sale', 'Clothing', 19.99, 5.00, '2024-11-12 10:52:01', '2024-11-13 17:03:00', 'S, M, L, XL', 'Men', 'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcQTOgLHXN0o4OgAHJmNDWgIkUJ94wmQkz4hK1v2_z30MAynEOHPbI3TQw-kSD2MY50WlB88nSSzqSW-BTP8l4O0wb2_20WdRvTTGilWcIw'),
(99990026, 'Women\'s Jumpsuit', 'Fashionable jumpsuit for women for a trendy look.', 'New Arrival', 'Clothing', 49.99, 15.00, '2024-11-12 10:52:01', '2024-11-13 17:03:19', 'S, M, L, XL', 'Women', 'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcTkfpjsE8IHo_fWfdq0tHD-vgfHLgrQpakDb-nMdxeI6PDuIW0rrRLtFIuZeTPG2fA7d6-QD1JQlYgABTV6YxQ0Gx8RZWgtlEbpCBcEoXd_mxPZJUSOtN_cCA'),
(99990027, 'Unisex Sweater', 'Cozy sweater with a relaxed fit, suitable for both men and women.', 'Sale', 'Clothing', 34.99, 10.00, '2024-11-12 10:52:01', '2024-11-13 17:09:24', 'S, M, L, XL', 'Unisex', 'https://i.etsystatic.com/51143004/r/il/ddef4d/6347572601/il_fullxfull.6347572601_pd6j.jpg'),
(99990028, 'Men\'s T-shirt', 'Comfortable cotton t-shirt for men in various colors.', 'Casual', 'T-shirt', 19.99, 10.00, '2024-11-12 11:06:02', '2024-11-13 17:04:49', 'S, M, L, XL', 'Men', 'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcR9ydgHPIulA4Y9BOKR4U1-ZVHz7NCudrGTkWV2aAYWGAp4BZlA78GhkOOrdB1d_hIkMULY47l5dcs9vrDEuY3_vI8mM6kYrcRYpIfmoXjBl-MRQO6uc8mH'),
(99990030, 'Unisex Hoodie', 'Cozy hoodie with a simple design for both men and women.', 'Casual', 'Hoodie', 29.99, 5.00, '2024-11-12 11:06:02', '2024-11-13 17:05:07', 'S, M, L, XL, XXL', 'Unisex', 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSrvRN4kiH1LgE-5spW9pKBWPg3X89mwWSnSxKxHK6Z5diHry17iKyeU07jssZ5iUk8CMVquvgRJ4hf2nRysFY2zNDcpSfWOTszErBiIg51jFnD5b6GIuwF'),
(99990031, 'Men\'s Jeans', 'Classic denim jeans with a comfortable fit.', 'Denim', 'Jeans', 49.99, 0.00, '2024-11-12 11:06:02', '2024-11-13 17:05:21', '30, 32, 34, 36, 38', 'Men', 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcTxEinlzzdg5R0H8SLrSLh3G6dYOlJq83phhuG5fhHZcBFp6mXyI5c7-IFEKv-Pyo-4kutKJ3Hnhw2O5aYNkViS03EFcTvze-5XyZU8ufEVIccb1fHupslkEg'),
(99990032, 'Women\'s Sweater', 'Warm wool sweater ideal for the colder months.', 'Winter', 'Sweater', 59.99, 20.00, '2024-11-12 11:06:02', '2024-11-13 17:06:22', 'S, M, L, XL', 'Women', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQdu8Rkns9GkmYTfEG9gDXLGY9DPlecPFduQ&s'),
(99990033, 'Men\'s Sneakers', 'Stylish and comfortable sneakers for men.', 'Sneakers, Sport, Casual', 'Shoes', 59.99, 10.00, '2024-11-12 11:06:02', '2024-11-13 17:06:43', '7, 8, 9, 10, 11', 'Men', 'https://m.media-amazon.com/images/I/515-c0mpUKL._AC_UY1000_.jpg'),
(99990034, 'Women\'s Top', 'Chic Top with a relaxed fit.', 'Casual, Blouse, Office', 'Blouse', 24.99, 5.00, '2024-11-12 11:06:02', '2024-11-15 05:20:24', 'S, M, L, XL', 'Women', 'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcTtcuBqerej9SslADOBnrzd0sFTAQ_Rkx5x_UA6uv7EVmIsKU-LNPakDiFfEi7rbJBjWdpsLbiAOzW_BbNvj2X-o6O94JhkcNuC23ao4F1bIErTJCUlayiB3w'),
(99990035, 'Unisex Backpack', 'Durable and spacious backpack, great for travel or school.', 'Travel, Backpack, Unisex', 'Bag', 39.99, 0.00, '2024-11-12 11:06:02', '2024-11-13 17:09:48', 'One Size', 'Unisex', 'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcQA7lg3uaS__tKyW_tuzipTnX3DhbS7RRJ0zkqbAui4roe9uX1OuNJVVSn2vshSKl4eB7KtdDCc4ZWaJoJMHagEHLKjOFxSmY3PNoqOIEdXGHA2J0LeNqT8Dg'),
(99990036, 'Women\'s Skirt', 'A fashionable skirt with a trendy design.', 'Trendy', 'Skirt', 34.99, 10.00, '2024-11-12 11:06:02', '2024-11-13 17:07:36', 'S, M, L, XL', 'Women', 'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcQ_NTdcU9V8ZJ-GJOwHH3bhssBugG8p5w6kBJJwhrvKTn0zPYt6k904Cf5hiwFTFYsq1KeRdtus3RdCfImE9dFL_V9Fnnxv45YAPV4cwIJEGdpEwenSILM'),
(99990040, 'Men\'s Watch', 'Classic men\'s wristwatch with a leather strap.', 'Accessories, Watch, Luxury', 'Watch', 129.99, 10.00, '2024-11-12 11:06:02', '2024-11-13 17:08:31', 'One Size', 'Men', 'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcQf1ov3Wv3biiVeZWaggIkkoJxx_Zl-sO1KFCnuZFTDte2NBHO4-CXxhCNKrSfA5EEaeklj5clYBj1D05khvAhH-GPAnc3956EhcJ-srAlhOB4-g_Y8ejog'),
(99990041, 'Women\'s Scarf', 'Soft cashmere scarf in multiple colors.', 'Winter', 'Scarf', 19.99, 5.00, '2024-11-12 11:06:02', '2024-11-13 17:10:49', 'One Size', 'Women', 'https://i.etsystatic.com/6559042/r/il/880194/3661854959/il_fullxfull.3661854959_7f2h.jpg'),
(99990044, 'Women\'s Dress', 'Elegant summer dress with floral design, perfect for casual outings.', 'Suggested', 'Dress', 39.99, 15.00, '2024-11-12 11:06:03', '2024-11-13 17:04:22', 'S, M, L, XL', 'Women', 'https://images.meesho.com/images/products/442047041/pknta_1200.jpg'),
(99990045, 'Cream Hoodie', 'Cozy hoodie with a simple design for both men and women.', 'Suggested', 'Hoodie', 29.99, 5.00, '2024-11-12 11:06:03', '2024-11-14 15:20:33', 'S, M, L, XL, XXL', 'Unisex', 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcS8bxWLehu7bbZAzIJCb6iDGyW-JtwO_hSOB9obuHQHVVRJD3HS2DuQLT38iXz1UOGpMpK0gn6jLs7N3QBJe1ZS-esPn10eb5RV6jDeccQTI_-2a40db228'),
(99990046, 'Men\'s Jeans', 'Classic denim jeans with a comfortable fit.', 'Suggested', 'Jeans', 49.99, 0.00, '2024-11-12 11:06:03', '2024-11-13 17:02:11', '30, 32, 34, 36, 38', 'Men', 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcTxEinlzzdg5R0H8SLrSLh3G6dYOlJq83phhuG5fhHZcBFp6mXyI5c7-IFEKv-Pyo-4kutKJ3Hnhw2O5aYNkViS03EFcTvze-5XyZU8ufEVIccb1fHupslkEg'),
(99990047, 'Woolen Sweater ', 'Warm wool sweater ideal for the colder months.', 'Suggested', 'Sweater', 59.99, 20.00, '2024-11-12 11:06:03', '2024-11-15 05:17:11', 'S, M, L, XL', 'Women', 'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSoMSMWtqFwqdidoeJM_lys2MmGydsyBvO5JJi64q80ednwYTlOcVDFdV9ua3jciACDh_CAOWS7yj57pvNrMr4yRq4QrdmAwuLYMQ0S5Alg'),
(99990048, 'Men\'s Sneakers', 'Stylish and comfortable sneakers for men.', 'Suggested', 'Shoes', 59.99, 10.00, '2024-11-12 11:06:03', '2024-11-13 17:00:30', '7, 8, 9, 10, 11', 'Men', 'https://images.meesho.com/images/products/229636836/3d6l4_512.webp'),
(99990049, 'Green Women\'s Blouse', 'Chic blouse with a relaxed fit for everyday wear.', 'Suggested', 'Blouse', 24.99, 5.00, '2024-11-12 11:06:03', '2024-11-15 05:19:35', 'S, M, L, XL', 'Women', 'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRdVQaK_o7Qr14vGWPj1PcacDvmMz27bryOJB9k8ngMW1w_CB64thp4VPJp0ofl4NqXGkrsxwVBp6vOyhQNK-VPcJP9OCqEsDUqWBVS3G_7'),
(99990050, 'Unisex Backpack', 'Durable and spacious backpack, great for travel or school.', 'Suggested', 'Bag', 39.99, 0.00, '2024-11-12 11:06:03', '2024-11-13 17:11:06', 'One Size', 'Unisex', 'https://images.meesho.com/images/products/133881687/cokgx_1200.jpg'),
(99990051, 'Red Women\'s Skirt', 'A fashionable skirt with a trendy design.', 'Suggested', 'Skirt', 34.99, 10.00, '2024-11-12 11:06:03', '2024-11-15 05:21:13', 'S, M, L, XL', 'Women', 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcQv-n4JaXuo8PiYTKXWGMEmnu8VznUfUbC0QvsraFgzOYHADhXFUPdyePO-jztGn1VJx4QuErJg9_r9cMqQi8ISi7SSuhsxkzlwy5kLUDfZhaJQjya2nI4H'),
(99990054, 'Unisex Sunglasses', 'Stylish sunglasses suitable for both men and women.', 'Suggested', 'Sunglasses', 15.99, 0.00, '2024-11-12 11:06:03', '2024-11-13 16:50:56', 'One Size', 'Unisex', 'https://images.meesho.com/images/products/451639461/qbpso_1200.jpg'),
(99990055, 'Men\'s Watch', 'Classic men\'s wristwatch with a leather strap.', 'Suggested', 'Watch', 129.99, 10.00, '2024-11-12 11:06:03', '2024-11-13 16:50:24', 'One Size', 'Men', 'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcQDqIKqmug_vFsl77PDcm5inXFV8RrGJKA6gEJtxukl-CF6Y7oiItajhDbequMUCoMvIhnUNEoJA7wk1sqwZyKXi-YUd8I2Ri_-mdrmiFFh'),
(99990056, 'Women\'s Scarf', 'Soft cashmere scarf in multiple colors.', 'Suggested', 'Scarf', 19.99, 5.00, '2024-11-12 11:06:03', '2024-11-13 16:50:04', 'One Size', 'Women', 'https://i.etsystatic.com/21749771/r/il/0b2eaf/4422407801/il_fullxfull.4422407801_rj0m.jpg'),
(99990057, 'Unisex Cap', 'Comfortable cap with adjustable fit for all genders.', 'Suggested', 'Cap', 14.99, 0.00, '2024-11-12 11:06:03', '2024-11-13 16:49:08', 'One Size', 'Unisex', 'https://rukminim2.flixcart.com/image/1200/1200/xif0q/cap/f/i/g/-original-imagzrsjphrhzham.jpeg');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `email` varchar(50) NOT NULL,
  `phone` int(10) NOT NULL,
  `password` varchar(200) NOT NULL,
  `name` varchar(50) NOT NULL,
  `token` varchar(500) DEFAULT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`email`, `phone`, `password`, `name`, `token`, `id`) VALUES
('ashraf@.com', 909, '$2y$10$h/QkirWS03L9pMjln4zv5OyCPihk4xVxzcri6JN9ynsywYlei0mqK', 'ashraf004', 'df98bdb4d33c03a2b506d0f2c2478277e0f8f27abe3e1daae29576504e5fdb48075d75370fd2be90a92e5f64609ad35e', 2024000001),
('aa@.com', 1235, '$2y$10$CtRgT.QBhhyGhxjEi9eHe.IBB1TdkdmL/hPGQEEnxXR5K0TGMOynG', 'aa', 'a9ab9a9e82b7d35c92dcd1099621c5c994c1ad6045b3c8357981ce200a40771e34527e786cc66a8f9cbd9184094acb08', 2024000002),
('fhdhs', 2147483647, '$2y$10$T5Of3fzFM3hz.dTjKO6sF.3IMr5hquAKLA80kx6DSfLn4L3aPQwHK', 'ashtaf', NULL, 2024000003),
('ashrafali', 1234567891, '$2y$10$TEP73k3GcoeRH0vxWPLPIuPV7acQ7Nd.CHIKi/1YZVrc5dp6JB356', 'ashraf', '4e912b3be5749b716cd682ca02ea3d9099761ec8d57245543e851c5e774e341d12f25dfaf9ca83a56ae1356b33fe3d9a', 2024000004);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cart_id`),
  ADD KEY `idx_user_cart` (`id`),
  ADD KEY `idx_product_cart` (`product_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `cart_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99990058;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2024000005;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `cart_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `cart_ibfk_4` FOREIGN KEY (`id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `cart_ibfk_5` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
