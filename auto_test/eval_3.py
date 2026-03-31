def validate_task_three(result=None, device_id=None, backup_dir=None):
    """验证任务三：查看感兴趣的“Lifestyle”商品有几个，给出一个阿拉伯数字即可。"""

    # 检查result中的final_message是否包含相同的数字
    if result and "final_message" in result and result["final_message"] is not None:
        if "4" in result["final_message"]:
            return True

    return False


if __name__ == "__main__":
    result = validate_task_three()
    print(result)
