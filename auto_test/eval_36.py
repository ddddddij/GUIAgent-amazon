def validate_task_thirty_six(result=None, device_id=None, backup_dir=None):
    """验证任务三十六：计算各购物清单商品的平均价格，给出最低的平均价格，保留两位小数。"""
    if result and "final_message" in result and result["final_message"] is not None:
        if "29.48" in result["final_message"]:
            return True
    return False


if __name__ == "__main__":
    result = validate_task_thirty_six()
    print(result)
