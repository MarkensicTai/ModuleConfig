import com.example.buildconfig.generated.AppConfig
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * 测试生成的配置代码
 */
class TestGeneratedCode {
    
    @Before
    fun setUp() {
        // 测试前的准备工作
    }
    
    @Test
    fun testBasicStringFields() {
        // 测试基础字符串字段
        assertNotNull("API_BASE_URL should not be null", AppConfig.API_BASE_URL)
        assertEquals("https://api.example.com", AppConfig.API_BASE_URL)
        
        assertNotNull("APP_NAME should not be null", AppConfig.APP_NAME)
        assertEquals("BuildConfig Example", AppConfig.APP_NAME)
        
        assertEquals("", AppConfig.EMPTY_STRING)
        assertEquals("Hello \"World\" \n\t\r", AppConfig.SPECIAL_CHARS)
    }
    
    @Test
    fun testIntegerFields() {
        // 测试整型字段
        assertEquals(1, AppConfig.API_VERSION)
        assertEquals(0, AppConfig.ZERO_VALUE)
        assertEquals(-100, AppConfig.NEGATIVE_VALUE)
        assertEquals(2147483647, AppConfig.MAX_INT)
        assertEquals(3, AppConfig.MAX_RETRY_COUNT)
        assertEquals(10485760, AppConfig.CACHE_SIZE)
        assertEquals(1, AppConfig.DATABASE_VERSION)
    }
    
    @Test
    fun testLongFields() {
        // 测试长整型字段
        assertTrue("BUILD_TIMESTAMP should be a valid timestamp", AppConfig.BUILD_TIMESTAMP > 0L)
        assertEquals(9223372036854775807L, AppConfig.LARGE_NUMBER)
        assertEquals(1000L, AppConfig.MIN_TIMEOUT)
    }
    
    @Test
    fun testFloatAndDoubleFields() {
        // 测试浮点型字段
        assertEquals(1.0f, AppConfig.APP_VERSION_FLOAT, 0.001f)
        assertEquals(3.14159f, AppConfig.PRECISION_FLOAT, 0.00001f)
        
        assertEquals(3.14159265359, AppConfig.PI_VALUE, 0.000000001)
        assertEquals(1.23E-4, AppConfig.SCIENTIFIC_NOTATION, 0.000000001)
    }
    
    @Test
    fun testBooleanFields() {
        // 测试布尔型字段
        assertTrue("DEBUG_MODE should be true", AppConfig.DEBUG_MODE)
        assertFalse("PRODUCTION_MODE should be false", AppConfig.PRODUCTION_MODE)
    }
    
    @Test
    fun testArrayFields() {
        // 测试数组字段
        assertNotNull("BOOLEAN_ARRAY should not be null", AppConfig.BOOLEAN_ARRAY)
        assertEquals(4, AppConfig.BOOLEAN_ARRAY.size)
        assertTrue(AppConfig.BOOLEAN_ARRAY[0])
        assertFalse(AppConfig.BOOLEAN_ARRAY[1])
        
        assertNotNull("INT_ARRAY should not be null", AppConfig.INT_ARRAY)
        assertEquals(5, AppConfig.INT_ARRAY.size)
        assertEquals(10, AppConfig.INT_ARRAY[0])
        assertEquals(50, AppConfig.INT_ARRAY[4])
        
        assertNotNull("CUSTOM_ARRAY should not be null", AppConfig.CUSTOM_ARRAY)
        assertEquals(3, AppConfig.CUSTOM_ARRAY.size)
    }
    
    @Test
    fun testListFields() {
        // 测试列表字段
        assertNotNull("SIMPLE_STRING_LIST should not be null", AppConfig.SIMPLE_STRING_LIST)
        assertEquals(3, AppConfig.SIMPLE_STRING_LIST.size)
        assertTrue(AppConfig.SIMPLE_STRING_LIST.contains("apple"))
        assertTrue(AppConfig.SIMPLE_STRING_LIST.contains("banana"))
        assertTrue(AppConfig.SIMPLE_STRING_LIST.contains("cherry"))
        
        assertNotNull("SIMPLE_INT_LIST should not be null", AppConfig.SIMPLE_INT_LIST)
        assertEquals(5, AppConfig.SIMPLE_INT_LIST.size)
        assertEquals(Integer.valueOf(1), AppConfig.SIMPLE_INT_LIST[0])
        
        assertNotNull("TABLE_NAMES should not be null", AppConfig.TABLE_NAMES)
        assertEquals(4, AppConfig.TABLE_NAMES.size)
        assertTrue(AppConfig.TABLE_NAMES.contains("users"))
        
        assertNotNull("RETRY_INTERVALS should not be null", AppConfig.RETRY_INTERVALS)
        assertEquals(5, AppConfig.RETRY_INTERVALS.size)
        assertEquals(Integer.valueOf(1000), AppConfig.RETRY_INTERVALS[0])
        assertEquals(Integer.valueOf(16000), AppConfig.RETRY_INTERVALS[4])
    }
    
    @Test
    fun testMapFields() {
        // 测试Map字段
        assertNotNull("STRING_STRING_MAP should not be null", AppConfig.STRING_STRING_MAP)
        assertEquals(3, AppConfig.STRING_STRING_MAP.size)
        assertEquals("John", AppConfig.STRING_STRING_MAP["name"])
        assertEquals("New York", AppConfig.STRING_STRING_MAP["city"])
        assertEquals("USA", AppConfig.STRING_STRING_MAP["country"])
        
        assertNotNull("STRING_INT_MAP should not be null", AppConfig.STRING_INT_MAP)
        assertEquals(3, AppConfig.STRING_INT_MAP.size)
        assertEquals(Integer.valueOf(1), AppConfig.STRING_INT_MAP["count"])
        
        assertNotNull("STRING_BOOLEAN_MAP should not be null", AppConfig.STRING_BOOLEAN_MAP)
        assertTrue(AppConfig.STRING_BOOLEAN_MAP["debug"]!!)
        assertFalse(AppConfig.STRING_BOOLEAN_MAP["production"]!!)
        
        assertNotNull("PERFORMANCE_THRESHOLDS should not be null", AppConfig.PERFORMANCE_THRESHOLDS)
        assertEquals(3, AppConfig.PERFORMANCE_THRESHOLDS.size)
        assertEquals(0.8, AppConfig.PERFORMANCE_THRESHOLDS["cpu_threshold"]!!, 0.001)
    }
    
    @Test
    fun testSpecialCases() {
        // 测试特殊情况
        assertNotNull("QUOTED_LIST should not be null", AppConfig.QUOTED_LIST)
        assertTrue(AppConfig.QUOTED_LIST.contains("Hello \"World\""))
        
        assertNotNull("SINGLE_ELEMENT_LIST should not be null", AppConfig.SINGLE_ELEMENT_LIST)
        assertEquals(1, AppConfig.SINGLE_ELEMENT_LIST.size)
        assertEquals("only_one", AppConfig.SINGLE_ELEMENT_LIST[0])
        
        assertNotNull("SINGLE_ENTRY_MAP should not be null", AppConfig.SINGLE_ENTRY_MAP)
        assertEquals(1, AppConfig.SINGLE_ENTRY_MAP.size)
        assertEquals(Integer.valueOf(42), AppConfig.SINGLE_ENTRY_MAP["single"])
    }
    
    @Test
    fun testNegativeValues() {
        // 测试负数值
        assertNotNull("NEGATIVE_INT_LIST should not be null", AppConfig.NEGATIVE_INT_LIST)
        assertTrue(AppConfig.NEGATIVE_INT_LIST.contains(-1))
        assertTrue(AppConfig.NEGATIVE_INT_LIST.contains(-2))
        assertTrue(AppConfig.NEGATIVE_INT_LIST.contains(-3))
        assertTrue(AppConfig.NEGATIVE_INT_LIST.contains(0))
        assertTrue(AppConfig.NEGATIVE_INT_LIST.contains(1))
    }
    
    @Test
    fun testConfigIntegrity() {
        // 测试配置完整性
        println("=== 配置测试报告 ===")
        println("API URL: ${AppConfig.API_BASE_URL}")
        println("API Version: ${AppConfig.API_VERSION}")
        println("Debug Mode: ${AppConfig.DEBUG_MODE}")
        println("Build Time: ${AppConfig.BUILD_TIMESTAMP}")
        println("Database: ${AppConfig.DATABASE_NAME} v${AppConfig.DATABASE_VERSION}")
        println("Cache Size: ${AppConfig.CACHE_SIZE}")
        println("Table Count: ${AppConfig.TABLE_NAMES.size}")
        println("===================")
        
        // 验证关键配置不为空
        assertNotNull("API_BASE_URL should not be null", AppConfig.API_BASE_URL)
        assertNotNull("DATABASE_NAME should not be null", AppConfig.DATABASE_NAME)
        assertTrue("API_VERSION should be positive", AppConfig.API_VERSION > 0)
        assertTrue("DATABASE_VERSION should be positive", AppConfig.DATABASE_VERSION > 0)
    }
}