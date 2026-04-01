def validate_task_twenty(result=None, device_id=None, backup_dir=None):
    """验证任务二十：搜索'electronics',查看评分大于4.8的商品有多少个。"""
    if result and "final_message" in result and result["final_message"] is not None:
        if "3" in result["final_message"]:
            return True
    return False


if __name__ == "__main__":
    result = validate_task_twenty()
    print(result)
