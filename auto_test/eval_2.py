def validate_task_two(result=None, device_id=None, backup_dir=None):
    """验证任务二：计算购物车中的商品总价格，给出一个阿拉伯数字即可。"""

    # 检查result中的final_message是否包含相同的数字
    if result and "final_message" in result and result["final_message"] is not None:
        if "5472.84" in result["final_message"]:
            return True

    return False


if __name__ == "__main__":
    result = validate_task_two()
    print(result)
