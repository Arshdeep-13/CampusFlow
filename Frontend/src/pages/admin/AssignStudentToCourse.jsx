import { useState, useEffect } from 'react';
import { Form, Select, Button, message, Card, Row, Col } from 'antd';
import { adminService } from '../../services/adminService';

const { Option } = Select;

const AssignStudentToCourse = () => {
  const [form] = Form.useForm();
  const [students, setStudents] = useState([]);
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [fetching, setFetching] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setFetching(true);
    try {
      const [studentsData, coursesData] = await Promise.all([
        adminService.getStudents(),
        adminService.getCourses(),
      ]);
      setStudents(studentsData || []);
      setCourses(coursesData || []);
    } catch (err) {
      message.error('Failed to fetch data');
      console.error('Error fetching data:', err);
    } finally {
      setFetching(false);
    }
  };

  const handleSubmit = async (values) => {
    setLoading(true);
    try {
      await adminService.assignStudentToCourse(values.studentId, values.courseId);
      message.success('Student assigned to course successfully');
      form.resetFields();
    } catch (err) {
      message.error(err.response?.data?.message || 'Failed to assign student to course');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="h-full flex flex-col" style={{ padding: '1rem' }}>
      <div className="mb-6">
        <h1 className="text-2xl font-bold">Assign Student to Course</h1>
      </div>

      <Row justify="center">
        <Col xs={24} sm={20} md={16} lg={12}>
          <Card>
            <Form
              form={form}
              layout="vertical"
              onFinish={handleSubmit}
            >
              <Form.Item
                name="studentId"
                label="Student"
                rules={[{ required: true, message: 'Please select a student' }]}
              >
                <Select 
                  placeholder="Select Student" 
                  showSearch
                  size="large"
                  loading={fetching}
                  filterOption={(input, option) =>
                    (option?.children ?? '').toLowerCase().includes(input.toLowerCase())
                  }
                >
                  {students.map((student) => (
                    <Option key={student.id} value={student.id}>
                      {student.name}
                    </Option>
                  ))}
                </Select>
              </Form.Item>

              <Form.Item
                name="courseId"
                label="Course"
                rules={[{ required: true, message: 'Please select a course' }]}
              >
                <Select 
                  placeholder="Select Course" 
                  showSearch
                  size="large"
                  loading={fetching}
                  filterOption={(input, option) =>
                    (option?.children ?? '').toLowerCase().includes(input.toLowerCase())
                  }
                >
                  {courses.map((course) => (
                    <Option key={course.id} value={course.id}>
                      {course.name} ({course.courseCode})
                    </Option>
                  ))}
                </Select>
              </Form.Item>

              <Form.Item className="mb-0">
                <Button 
                  type="primary" 
                  htmlType="submit" 
                  loading={loading}
                  size="large"
                  block
                >
                  Assign
                </Button>
              </Form.Item>
            </Form>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default AssignStudentToCourse;
