import cv2
import argparse

def denoise_image(input_path):
    # 读取输入图像
    image = cv2.imread(input_path)
    print(input_path)
    if image is None:
        raise ValueError("输入的图片路径无效，请检查路径")

    # 使用中值滤波降噪
    denoised_image = cv2.medianBlur(image, 5)

    # 覆盖原文件
    cv2.imwrite(input_path, denoised_image)

    print(f"图像降噪完成，结果已覆盖原文件：{input_path}")

if __name__ == "__main__":
    # 使用 argparse 接收输入路径
    parser = argparse.ArgumentParser(description="图像降噪脚本")
    parser.add_argument("-i", "--input", required=True, help="输入图片路径")
    args = parser.parse_args()

    # 调用降噪函数
    denoise_image(args.input)