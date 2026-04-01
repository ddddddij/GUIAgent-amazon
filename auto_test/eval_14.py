def validate_task_fourteen(result=None, device_id=None, backup_dir=None):
    """验证任务十四：查看已取消的订单，告诉我该订单的总金额是多少。"""
    if result and "final_message" in result and result["final_message"] is not None:
        if "449.99" in result["final_message"]:
            return True
    return False


if __name__ == "__main__":
    result = validate_task_fourteen()
    print(result)
