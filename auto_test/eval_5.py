def validate_task_five(result=None, device_id=None, backup_dir=None):
    """验证任务五：查看我的订单列表，计算我共有多少个订单，给出一个阿拉伯数字即可。"""

    # 检查result中的final_message是否包含相同的数字
    if result and "final_message" in result and result["final_message"] is not None:
        if "12" in result["final_message"]:
            return True

    return False


if __name__ == "__main__":
    result = validate_task_five()
    print(result)
