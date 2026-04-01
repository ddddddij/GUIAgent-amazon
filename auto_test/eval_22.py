def validate_task_twenty_two(result=None, device_id=None, backup_dir=None):
    """验证任务二十二：在购物车中，找出单价最高的三件商品，计算这三件商品的总价。"""
    if result and "final_message" in result and result["final_message"] is not None:
        if "3118.98" in result["final_message"]:
            return True
    return False


if __name__ == "__main__":
    result = validate_task_twenty_two()
    print(result)
