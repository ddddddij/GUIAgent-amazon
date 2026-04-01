def validate_task_twenty_eight(result=None, device_id=None, backup_dir=None):
    """验证任务二十八：在兴趣页面，分别查看四个分类，计算每个分类下所有商品的平均评分，给我最高的平均评分，保留两位小数。"""
    if result and "final_message" in result and result["final_message"] is not None:
        if "4.83" in result["final_message"]:
            return True
    return False


if __name__ == "__main__":
    result = validate_task_twenty_eight()
    print(result)
